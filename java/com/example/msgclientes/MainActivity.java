package com.example.msgclientes;

import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    private Button btnCargar;
    private TextView txtResultado;
    private String enviado=" ";
    private String telefono;
    private String mensage;
    private List<Cliente> clientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCargar = (Button)findViewById(R.id.btnCargar);
        txtResultado = (TextView)findViewById(R.id.txtResultado);

        btnCargar.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                //Sin Tarea AsÃ­ncrona
                //SmsParserSax saxparser =
                //		new SmsParserSax("http://192.168.35.69/rss/parasms.xml");

                //clientes = saxparser.parse();

                //Tratamos la lista de clientes
                //Por ejemplo: escribimos los tÃ­tulos en pantalla
                //txtResultado.setText("");
                //for(int i=0; i<clientes.size(); i++)
                //{
                //	txtResultado.setText(
                //		txtResultado.getText().toString() +
                //			System.getProperty("line.separator") +
                //			clientes.get(i).getNombre());
                //}

                //Con Tarea AsÃ­ncrona
                CargarXmlTask tarea = new CargarXmlTask();
                tarea.execute("http://192.168.35.69/parasms.xml");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //Tarea AsÃ­ncrona para cargar un XML en segundo plano
    private class CargarXmlTask extends AsyncTask<String,Integer,Boolean> {

        protected Boolean doInBackground(String... params) {

            SmsParserSax saxparser =
                    new SmsParserSax(params[0]);

            clientes = saxparser.parse();

            return true;
        }

        protected void onPostExecute(Boolean result) {

            //Tratamos la lista de noticias
            //Por ejemplo: escribimos los tÃ­tulos en pantalla
            txtResultado.setText("");
            for(int i=0; i<clientes.size(); i++)
            {
                telefono=clientes.get(i).getTelefono();
                mensage=clientes.get(i).getMensaje();

                txtResultado.setText(
                        txtResultado.getText().toString() +
                                System.getProperty("line.separator") +
                                clientes.get(i).getNombre() +
                                enviaSms(telefono, mensage) +
                                System.getProperty("line.separator"));
            }
        }
    }

    protected String enviaSms(String telefono, String mensage) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(telefono,null,mensage,null,null);
            return " -Mensaje Enviado";
        }

        catch (Exception e) {
            e.printStackTrace();
            return " -Mensaje fallido";
        }

    }
}