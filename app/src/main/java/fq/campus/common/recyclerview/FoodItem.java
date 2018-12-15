package fq.campus.common.recyclerview;



public class FoodItem extends BasicItem{
    private String foodImageByte;

    public FoodItem(String name, String pic){
        this.setName(name);
        this.foodImageByte = getFoodImageByte();
    }

    public String getFoodImageByte(){
        return foodImageByte;
    }
}
