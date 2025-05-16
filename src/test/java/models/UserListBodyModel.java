package models;

import lombok.Data;

@Data
public class UserListBodyModel {
    Integer page, per_page, total, total_pages;
}
