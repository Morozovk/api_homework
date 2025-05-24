package models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class UserListBodyModel {
    Integer page, per_page, total, total_pages;
}
