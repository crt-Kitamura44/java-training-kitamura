package jp.co.careritztc.sample.dto;

import java.time.LocalDate;

/**
 * 祝日情報DTO.
 */
public class HolidaysDto {
  private Integer id;
  private LocalDate dateAt;
  private String name;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public LocalDate getDateAt() {
    return dateAt;
  }

  public void setDateAt(LocalDate dateAt) {
    this.dateAt = dateAt;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
