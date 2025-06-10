package br.edu.ibmec.cloud.Ecommerce.dtos.users;

import br.edu.ibmec.cloud.Ecommerce.models.User;
import lombok.Data;

@Data
public class UpdateUserDto {

    private String name;
    private String password;

    public void applyTo(User user) {
        if (name != null) {
            user.setName(name);
        }
        if (password != null) {
            user.setPassword(password);
        }
    }
}
