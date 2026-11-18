package dev.drmn.vocabularioapp;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {
    private static VolleySingleton instancia;
    private final RequestQueue cola;
    private VolleySingleton(Context ctx) {
        cola = Volley.newRequestQueue(ctx.getApplicationContext());
    }
    public static synchronized VolleySingleton getInstance(Context ctx) {
        if (instancia == null) instancia = new VolleySingleton(ctx);
        return instancia;
    }
    public <T> void agregar(Request<T> request) { cola.add(request); }
}
