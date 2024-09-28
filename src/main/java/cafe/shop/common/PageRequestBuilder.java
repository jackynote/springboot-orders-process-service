package cafe.shop.common;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class PageRequestBuilder {
    
    public static PageRequest buildPageRequest(int page, int size, String sortString) {
        Sort sort = parseSortString(sortString);
        return PageRequest.of(page, size, sort);
    }

    private static Sort parseSortString(String sortString) {
        if (sortString == null || sortString.isEmpty()) {
            return Sort.unsorted();
        }
        
        String[] sortParams = sortString.split(",");
        Sort.Order[] orders = new Sort.Order[sortParams.length];
        for (int i = 0; i < sortParams.length; i++) {
            String[] parts = sortParams[i].split(":");
            String property = parts[0];
            String direction = parts[1];
            orders[i] = new Sort.Order(Sort.Direction.fromString(direction), property);
        }
        return Sort.by(orders);
    }
}
