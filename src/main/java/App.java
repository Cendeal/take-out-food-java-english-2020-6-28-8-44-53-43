import java.util.ArrayList;
import java.util.List;

/*
 * This Java source file was generated by the Gradle 'init' task.
 */
public class App {
    private ItemRepository itemRepository;
    private SalesPromotionRepository salesPromotionRepository;

    public App(ItemRepository itemRepository, SalesPromotionRepository salesPromotionRepository) {
        this.itemRepository = itemRepository;
        this.salesPromotionRepository = salesPromotionRepository;
    }

    public String bestCharge(List<String> inputs) {
        StringBuilder result = new StringBuilder("============= Order details =============\n");
        List<OrderItem> orderItems = findOrderItemsByInputs(inputs);
        orderItems.forEach(orderItem -> { result.append(orderItem.toString()); });
        result.append("-----------------------------------\n");
        double actualPrice = usingPromotion(orderItems, result);
        result.append(String.format("Total：%.0f yuan\n", actualPrice))
                .append("===================================");
        return result.toString();
    }

    public List<OrderItem> findOrderItemsByInputs(List<String> inputs) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (String input : inputs) {
            String[] item_str = input.replaceAll(" ", "").split("x");
            //find item
            for (Item item : this.itemRepository.findAll()) {
                if (item.getId().equals(item_str[0])) {
                    orderItems.add(new OrderItem(item, Integer.parseInt(item_str[1])));
                    break;
                }
            }
        }
        return orderItems;
    }

    public SalesPromotion findSalePromotionByRelatedItem(Item item) {
        for (SalesPromotion salesPromotion : this.salesPromotionRepository.findAll()) {
            if (salesPromotion.getRelatedItems().indexOf(item.getId()) >= 0) {
                return salesPromotion;
            }
        }
        return null;
    }

    public double calculateTotalPrice(List<OrderItem> orderItems) {
        double totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.calculateAmount();
        }
        return totalPrice;
    }

    public double deductWhenReach30SaveTotalSavePrice(double totalPrice) {
        return totalPrice>=30?6:0;
    }

    public double halfPriceSalesPromotionTotalSavePrice(List<OrderItem> orderItems) {
        double totalSavePrice = 0;
        for (OrderItem orderItem : orderItems) {
            SalesPromotion salesPromotion = findSalePromotionByRelatedItem(orderItem.getItem());
            if (salesPromotion != null) {
                totalSavePrice += orderItem.calculateAmount() * 0.5;
            }
        }
        return totalSavePrice;
    }

    public double usingPromotion(List<OrderItem> orderItems, StringBuilder result) {
        double totalPrice = calculateTotalPrice(orderItems);
        double halfPrices = halfPriceSalesPromotionTotalSavePrice(orderItems);
        double deduct = deductWhenReach30SaveTotalSavePrice(totalPrice);
        double actualSave = 0;
        if (deduct > 0 || halfPrices > 0) {
            result.append("Promotion used:\n");
            if (deduct >= halfPrices) {
                actualSave = deduct;
                result.append("满30减6 yuan，saving 6 yuan\n");
            } else {
                actualSave = halfPrices;
                result.append(String.format("Half price for certain dishes (Braised chicken，Cold noodles)，saving %.0f yuan\n", halfPrices));
            }
            result.append("-----------------------------------\n");
        }
        return totalPrice - actualSave;
    }
}
