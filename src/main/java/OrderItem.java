public class OrderItem {
    private Item item;
    private int total;

    private SalesPromotion salesPromotion;

    OrderItem(Item item, int total) {
        this.item = item;
        this.total = total;
    }


    public Item getItem() {
        return item;
    }

    public int getTotal() {
        return total;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public SalesPromotion getSalesPromotion() {
        return salesPromotion;
    }

    public void setSalesPromotion(SalesPromotion salesPromotion) {
        this.salesPromotion = salesPromotion;
    }
}
