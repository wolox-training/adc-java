package wolox.training.models;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class PasswordReset {

    @NotNull
    @ApiModelProperty(required = true, notes = "Old password of the user")
    private String oldPassword;

    @NotNull
    @ApiModelProperty(required = true, notes = "New password of the user")
    private String newPassword;

    public void setOldPassword(String oldPassword) {
        checkNotNull(oldPassword, "Please check oldPassword field, its null");
        this.oldPassword = oldPassword;
    }

    public void setNewPassword(String newPassword) {
        checkNotNull(newPassword, "Please check newPassword field, its null");
        checkArgument(!oldPassword.equals(newPassword), "Passwords must not be the same");
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}