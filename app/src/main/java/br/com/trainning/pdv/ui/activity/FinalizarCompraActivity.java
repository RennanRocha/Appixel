package br.com.trainning.pdv.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import br.com.trainning.pdv.R;
import br.com.trainning.pdv.domain.model.Carrinho;
import br.com.trainning.pdv.domain.util.Util;
import butterknife.Bind;
import se.emilsjolander.sprinkles.Query;

public class FinalizarCompraActivity extends BaseActivity {

    @Bind(R.id.dataHoje)
    TextView dataHoje;

    @Bind(R.id.quantidadeItens)
    TextView quantidadeItens;

    @Bind(R.id.valorTotal)
    TextView valorTotal;

    @Bind(R.id.imageButtonCancel)
    ImageButton imageButtonCancel;

    @Bind(R.id.imageButtonConfirm)
    ImageButton imageButtonConfirm;

    String idCompra;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalizar_compra);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        intent = getIntent();

        int qtd = intent.getIntExtra("quantidadeItens", 0);
        double valor   = intent.getDoubleExtra("valorTotal", 0.0d);
        idCompra = intent.getStringExtra("idCompra");

        quantidadeItens.setText(String.valueOf(qtd));
        valorTotal.setText(String.valueOf(valor));
        dataHoje.setText(Util.getData());

        imageButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        imageButtonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Carrinho carrinho = Query.one(Carrinho.class, "select * from carrinho where id_compra = ?", idCompra).get();
                carrinho.setEncerrada(1);
                carrinho.save();

                finish();
            }
        });


    }

}
