package app.data.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {
    ADMIN(0,"Администратор"),
    CLIENT(1,"Клиент");
    private int id;
    private String name;

    @Override
    public String toString(){
        return name;
    }

    }
