
package examsandquestions;

import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepository extends JpaRepository<Exam, Long> {
    
    @EntityGraph(attributePaths = {"questions"})
    List<Exam> findAll();
    
    List<Exam> findAllByOrderByExamDateDesc();
    
}
