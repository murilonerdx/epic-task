package com.murilonerdx.epictask.entities.enums;

public enum Role {

    ADMIN(1, "ADMIN"),
    USER(2, "USER");

    private int cod;
    private String descricao;

    private Role(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public int getCod() {
        return cod;
    }

    public String getDescricao () {
        return descricao;
    }

    public static Role toEnum(Integer cod) {

        if (cod == null) {
            return null;
        }

        for (Role x : Role.values()) {
            if (cod.equals(x.getCod())) {
                return x;
            }
        }

        throw new IllegalArgumentException("Id inválido: " + cod);
    }

}
