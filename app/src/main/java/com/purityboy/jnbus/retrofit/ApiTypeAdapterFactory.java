package com.purityboy.jnbus.retrofit;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.purityboy.jnbus.utils.L;

import java.io.IOException;

/**
 * Created by John on 2016/11/22.
 */

public class ApiTypeAdapterFactory implements TypeAdapterFactory {

    private String dataElementName;

    public ApiTypeAdapterFactory(String dataElementName) {
        this.dataElementName = dataElementName;
    }

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        final TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);
        final TypeAdapter<JsonElement> elementTypeAdapter = gson.getAdapter(JsonElement.class);

        return new TypeAdapter<T>() {
            @Override
            public void write(JsonWriter out, T value) throws IOException {
                delegate.write(out, value);
            }

            @Override
            public T read(JsonReader in) throws IOException {
                JsonElement jsonElement = elementTypeAdapter.read(in);
                if (jsonElement.isJsonObject()) {
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    if (jsonObject.has(dataElementName)) {
                        jsonElement = jsonObject.get(dataElementName);
                        L.i("jsonElement:" + jsonElement.getAsString());
                    }
                }
                return delegate.fromJsonTree(jsonElement);
            }
        };
    }
}
