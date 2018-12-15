package fq.campus.common.recyclerview;

public class BasicItem {
    private String name;
    public BasicItem(){

    }
    public BasicItem(String m_name){
        this.name = m_name;
    }
    public void setName(String m_name){
        this.name = m_name;
    }
    public String getName() {
        return name;
    }
    public String getString(){return getName();}

}
