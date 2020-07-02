public class OrderItem {
    private int count;
    private Item item;

    public OrderItem(Item item, int count) {
        this.count = count;
        this.item = item;

    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public double calculateAmount() {
        return this.count*this.item.getPrice();
    }

    @Override
    public String toString() {
        return String.format("%s x %d = %.0f yuan\n", item.getName(), count, calculateAmount());
    }
}
