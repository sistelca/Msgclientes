package com.example.msgclientes;

/**
 * Created by Luis on 09-05-2017.
 */

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;

import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.sax.StartElementListener;
import android.util.Xml;

public class SmsParserSax {
    private URL smsUrl;
    private Cliente clienteActual;

    public SmsParserSax(String url)
    {
        try
        {
            this.smsUrl = new URL(url);
        }
        catch (MalformedURLException e)
        {
            throw new RuntimeException(e);
        }
    }

    public List<Cliente> parse()
    {
        final List<Cliente> clientes = new ArrayList<Cliente>();

        RootElement root = new RootElement("clientes");
        Element cliente = root.getChild("cliente");

        cliente.setStartElementListener(new StartElementListener(){
            public void start(Attributes attrs) {
                clienteActual = new Cliente();
            }
        });

        cliente.setEndElementListener(new EndElementListener(){
            public void end() {
                clientes.add(clienteActual);
            }
        });

        cliente.getChild("nombre").setEndTextElementListener(
                new EndTextElementListener(){
                    public void end(String body) {
                        clienteActual.setNombre(body);
                    }
                });

        cliente.getChild("telefono").setEndTextElementListener(
                new EndTextElementListener(){
                    public void end(String body) {
                        clienteActual.setTelefono(body);
                    }
                });

        cliente.getChild("mensaje").setEndTextElementListener(
                new EndTextElementListener(){
                    public void end(String body) {
                        clienteActual.setMensaje(body);
                    }
                });

        try
        {
            Xml.parse(this.getInputStream(),
                    Xml.Encoding.UTF_8,
                    root.getContentHandler());
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }

        return clientes;
    }

    private InputStream getInputStream()
    {
        try
        {
            return smsUrl.openConnection().getInputStream();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
