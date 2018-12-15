package fq.campus.common.recyclerview;



public class CollectItem extends BasicItem{
    private String food;
    private String store;
    private String date;

    public CollectItem(String food_name, String store_name, String order_date){
        this.food = food_name;
        this.setName(this.food);
        this.store = store_name;
        this.date = order_date;
    }

    public String getFood() {
        return food;
    }

    public String getStore() {
        return store;
    }

    public String getDate(){
        return date;
    }
}
