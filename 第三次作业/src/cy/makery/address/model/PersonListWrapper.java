package cy.makery.address.model;

import java.util.List;

import javax.xml.bind.annotation.*;
/**
 * @XmlRootElement 定义根元素的名称。
 *@XmlElement 一个可选的名称，用来指定元素。
 * import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
 */

/**
 *助手类包装人员列表。这用于保存
 * XML列表。
 *
 * @author Marco Jakob
 */
@XmlRootElement(name = "persons")
public class PersonListWrapper {

    private List<Person> persons;

    @XmlElement(name = "person")
    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }
}