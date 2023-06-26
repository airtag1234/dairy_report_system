package actions.views;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import models.Employee;
import models.Report;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteView {


    private Integer id;


    private Report report;


    private Employee employee;


    private Integer favoriteFlag;


    private LocalDateTime favoriteAt;
}