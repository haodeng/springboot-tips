package demo.hao.model.ds1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Long quantities = 100L;

    public Inventory(String name) {
        this(null, name, 100L);
    }
}

