package br.com.trainning.pdv.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.mapzen.android.lost.api.LocationServices;
import com.mapzen.android.lost.api.LostApiClient;

import java.io.File;
import java.io.IOException;

import br.com.trainning.pdv.R;
import br.com.trainning.pdv.domain.image.Base64Util;
import br.com.trainning.pdv.domain.image.ImageInputHelper;
import br.com.trainning.pdv.domain.model.Produto;
import br.com.trainning.pdv.domain.network.APIClient;
import butterknife.Bind;
import jim.h.common.android.lib.zxing.config.ZXingLibConfig;
import jim.h.common.android.lib.zxing.integrator.IntentIntegrator;
import jim.h.common.android.lib.zxing.integrator.IntentResult;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class IncluirNovo extends BaseActivity implements ImageInputHelper.ImageActionListener{

    @Bind(R.id.editText)
    EditText descricao;
    @Bind(R.id.editText2)
    EditText unidade;
    @Bind(R.id.editText3)
    EditText codigoBarras;
    @Bind(R.id.editText4)
    EditText preco;
    @Bind(R.id.imageView)
    ImageView foto;
    @Bind(R.id.imageButton)
    ImageButton tiraFoto;
    @Bind(R.id.imageButton2)
    ImageButton selecionaGaleria;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    Produto produto;

    @Bind(R.id.imageButtonScan)
    ImageButton imageButtonScan;

    private ImageInputHelper imageInputHelper;

    private Callback<String> callbackCreateProduto;

    private ZXingLibConfig zxingLibConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incluir_novo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        configureProdutoCallback();

        zxingLibConfig = new ZXingLibConfig();
        zxingLibConfig.useFrontLight = true;

        //geolocalization
        LostApiClient lostApiClient = new LostApiClient.Builder(this).build();
        lostApiClient.connect();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                produto.setId(0L);
                produto.setDescricao(descricao.getText().toString());
                produto.setUnidade(unidade.getText().toString());
                produto.setCodigoBarras(codigoBarras.getText().toString());
                produto.setPreco(Double.parseDouble(preco.getText().toString()));

                Bitmap imagem = ((BitmapDrawable)foto.getDrawable()).getBitmap();

                produto.setFoto(Base64Util.encodeTobase64(imagem));
                produto.setAtivo(0);

                Location location = LocationServices.FusedLocationApi.getLastLocation();
                if (location != null) {
                    produto.setLatitude(location.getLatitude());
                    produto.setLongitude(location.getLongitude());
                }


                Log.d("PRODUTO", produto.toString());

                produto.save();

                new APIClient().getRestService().createProduto( produto.getCodigoBarras(),
                                                                produto.getDescricao(),
                                                                produto.getUnidade(),
                                                                produto.getPreco(),
                                                                produto.getFoto(),
                                                                produto.getAtivo(),
                                                                produto.getLatitude(),
                                                                produto.getLongitude(),
                                                                callbackCreateProduto
                                                                );

                finish();
            }
        });

        tiraFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageInputHelper.takePhotoWithCamera();
            }
        });

        selecionaGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageInputHelper.selectImageFromGallery();
            }
        });

        //Rotina para ler o barcode ao incluir produto
        imageButtonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                IntentIntegrator.initiateScan(IncluirNovo.this, zxingLibConfig);
            }
        });

        imageInputHelper = new ImageInputHelper(this);
        imageInputHelper.setImageActionListener(this);

        produto = new Produto();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageInputHelper.onActivityResult(requestCode, resultCode, data);


        switch (requestCode) {
            case IntentIntegrator.REQUEST_CODE:

                IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode,
                        resultCode, data);
                if (scanResult == null) {
                    return;
                }
                String result = scanResult.getContents();
                if (result != null) {
                    codigoBarras.setText(result);
                }
                break;
            default:
        }
    }

    @Override
    public void onImageSelectedFromGallery(Uri uri, File imageFile) {
        // cropping the selected image. crop intent will have aspect ratio 16/9 and result image
        // will have size 800x450
        imageInputHelper.requestCropImage(uri, 100, 100, 1, 1);
    }

    @Override
    public void onImageTakenFromCamera(Uri uri, File imageFile) {
        // cropping the taken photo. crop intent will have aspect ratio 16/9 and result image
        // will have size 800x450
        imageInputHelper.requestCropImage(uri, 100, 100, 1, 1);
    }

    @Override
    public void onImageCropped(Uri uri, File imageFile) {
        try {
            // getting bitmap from uri
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

            // showing bitmap in image view
            foto.setImageBitmap(bitmap);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void configureProdutoCallback() {

        callbackCreateProduto = new Callback<String>() {

            @Override public void success(String resultado, Response response) {
                Log.d("RETROFIT", "ENVIADO COM SUCESSO");
            }

            @Override public void failure(RetrofitError error) {

                Log.e("RETROFIT", "Error:"+error.getMessage());
            }
        };
    }

}
