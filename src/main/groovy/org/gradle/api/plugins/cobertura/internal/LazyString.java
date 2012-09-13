package org.gradle.api.plugins.cobertura.internal;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class LazyString implements Serializable {
    private Object value;

    public LazyString(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeObject(toString());
    }

    private void readObject(ObjectInputStream input) throws IOException, ClassNotFoundException {
        value = input.readObject();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LazyString that = (LazyString) o;

        if (value != null ? !toString().equals(that.toString()) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return value != null ? toString().hashCode() : 0;
    }
}