package com.sms.StudentManagmentSystem.group;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GroupUpdateStatus {

    @NotNull(message = "Status is not null")
    private GroupStatus status;
}
