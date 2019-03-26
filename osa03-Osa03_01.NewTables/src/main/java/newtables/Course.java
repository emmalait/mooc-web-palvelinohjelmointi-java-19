
package newtables;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course extends AbstractPersistable<Long> {
    
    private String name;
    
    @ManyToMany(mappedBy = "courses")
    private List<Student> students = new ArrayList<>();
    
}
