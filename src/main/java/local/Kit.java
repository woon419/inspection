package local;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="Kit_table")
public class Kit {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String name;
    private Long qty;
    private String type;

    @PostPersist
    public void onPostPersist(){
        KitRegistered kitRegistered = new KitRegistered();
        BeanUtils.copyProperties(this, kitRegistered);
        kitRegistered.publishAfterCommit();


    }

    @PostUpdate
    public void onPostUpdate(){
        KitChecked kitChecked = new KitChecked();
        BeanUtils.copyProperties(this, kitChecked);
        kitChecked.publishAfterCommit();


    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }




}
