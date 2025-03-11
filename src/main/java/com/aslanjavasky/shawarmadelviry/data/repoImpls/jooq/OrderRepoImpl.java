//package com.aslanjavasky.shawarmadelviry.data.repoImpls.jooq;
//
//import com.aslanjavasky.shawarmadelviry.domain.model.*;
//import com.aslanjavasky.shawarmadelviry.domain.repo.OrderRepo;
//import lombok.extern.slf4j.Slf4j;
//import org.jooq.DSLContext;
//import org.springframework.stereotype.Repository;
//import org.jooq.Record;
//
//import java.util.*;
//
//import static com.aslanjavasky.shawarmadelviry.generated.jooq.Tables.*;
//
//@Slf4j
//@Repository("ORwJOOQ")
//public class OrderRepoImpl implements OrderRepo {
//
//    private final DSLContext dslContext;
//
//    public OrderRepoImpl(DSLContext dslContext) {
//        this.dslContext = dslContext;
//    }
//
//    @Override
//    public IOrder saveOrder(IOrder order) {
//
//        if (order == null) throw new IllegalArgumentException("order cannot be null");
//
//        Long orderId = dslContext.insertInto(ORDERS)
//                .set(ORDERS.DATE_TIME, order.getDateTime())
//                .set(ORDERS.STATUS, order.getStatus().name())
//                .set(ORDERS.USER_ID, order.getUser().getId())
//                .set(ORDERS.TOTAL_PRICE, order.getTotalPrice())
//                .returningResult(ORDERS.ID)
//                .fetchOne()
//                .get(ORDERS.ID);
//
//        if (orderId == null) throw new RuntimeException("Failed to save orders, no generated key");
//        order.setId(orderId);
//
//        var queryCollection = order.getItemList().stream()
//                .map(item -> dslContext.insertInto(ORDERS_MENU_ITEMS)
//                        .set(ORDERS_MENU_ITEMS.ORDER_ID, order.getId())
//                        .set(ORDERS_MENU_ITEMS.MENU_ITEM_ID, item.getId()))
//                .toList();
//
//        dslContext.batch(queryCollection).execute();
//        return order;
//    }
//
//    @Override
//    public IOrder updateOrder(IOrder order) {
//
//        if (order == null) throw new IllegalArgumentException("order cannot be null");
//        int affectedRow = dslContext.update(ORDERS)
//                .set(ORDERS.DATE_TIME, order.getDateTime())
//                .set(ORDERS.STATUS, order.getStatus().name())
//                .set(ORDERS.USER_ID, order.getUser().getId())
//                .set(ORDERS.TOTAL_PRICE, order.getTotalPrice())
//                .where(ORDERS.ID.eq(order.getId()))
//                .execute();
//        if (affectedRow == 0) throw new RuntimeException("Failed to update order, no rows   affected");
//        return order;
//    }
//
//    @Override
//    public IOrder updateOrderStatus(Long orderId, OrderStatus status) {
//
//        if (orderId == null) throw new IllegalArgumentException("orderId cannot be null");
//        int affectedRow = dslContext.update(ORDERS)
//                .set(ORDERS.STATUS, status.name())
//                .where(ORDERS.ID.eq(orderId))
//                .execute();
//        if (affectedRow == 0) throw new RuntimeException("Failed to update order status, no rows affected");
//        return getOrderById(orderId);
//    }
//
//
//    @Override
//    public List<IOrder> getOrdersByUser(IUser user) {
//
//        if (user == null) throw new IllegalArgumentException("user cannot be null");
//
//        return dslContext.select(
//                        USERS.ID.as("user_id"),
//                        USERS.NAME.as("user_name"),
//                        USERS.EMAIL,
//                        USERS.PASSWORD,
//                        USERS.TELEGRAM,
//                        USERS.PHONE,
//                        USERS.ADDRESS,
//                        ORDERS.DATE_TIME,
//                        ORDERS.STATUS,
//                        ORDERS.TOTAL_PRICE,
//                        ORDERS_MENU_ITEMS.MENU_ITEM_ID,
//                        ORDERS_MENU_ITEMS.ORDER_ID,
//                        MENU_ITEMS.NAME.as("menu_item_name"),
//                        MENU_ITEMS.MENU_SECTION,
//                        MENU_ITEMS.PRICE
//                )
//                .from(ORDERS)
//                .join(USERS).on(ORDERS.USER_ID.eq(USERS.ID))
//                .join(ORDERS_MENU_ITEMS).on(ORDERS.ID.eq(ORDERS_MENU_ITEMS.ORDER_ID))
//                .join(MENU_ITEMS).on(ORDERS_MENU_ITEMS.MENU_ITEM_ID.eq(MENU_ITEMS.ID))
//                .where(USERS.ID.eq(user.getId()))
//                .orderBy(ORDERS.ID)
//                .fetchGroups(ORDERS_MENU_ITEMS.ORDER_ID)
//                .values()
//                .stream()
//                .map(records -> {
//                    IOrder order = null;
//                    for (Record record : records) {
//                        if (order == null) {
//                            order = createOrderFromRecord(record);
//                            order.setUser(createUserFromRecord(record));
//                            order.setItemList(new ArrayList<>());
//                        }
//                        order.getItemList().add(createMenuItemFromRecord(record));
//                    }
//                    return order;
//                })
//                .toList();
//    }
//
//
//    @Override
//    public List<IOrder> getOrdersByStatus(OrderStatus orderStatus) {
//
//        return dslContext.select(
//                        USERS.ID.as("user_id"),
//                        USERS.NAME.as("user_name"),
//                        USERS.EMAIL,
//                        USERS.PASSWORD,
//                        USERS.TELEGRAM,
//                        USERS.PHONE,
//                        USERS.ADDRESS,
//                        ORDERS_MENU_ITEMS.MENU_ITEM_ID,
//                        MENU_ITEMS.NAME.as("menu_item_name"),
//                        MENU_ITEMS.MENU_SECTION,
//                        MENU_ITEMS.PRICE,
//                        ORDERS_MENU_ITEMS.ORDER_ID,
//                        ORDERS.DATE_TIME,
//                        ORDERS.STATUS,
//                        ORDERS.TOTAL_PRICE)
//                .from(ORDERS)
//                .join(USERS).on(ORDERS.USER_ID.eq(USERS.ID))
//                .join(ORDERS_MENU_ITEMS).on(ORDERS.ID.eq(ORDERS_MENU_ITEMS.ORDER_ID))
//                .join(MENU_ITEMS).on(MENU_ITEMS.ID.eq(ORDERS_MENU_ITEMS.MENU_ITEM_ID))
//                .where(ORDERS.STATUS.eq(orderStatus.name()))
//                .orderBy(ORDERS.ID)
//                .fetchGroups(ORDERS_MENU_ITEMS.ORDER_ID)
//                .values()
//                .stream()
//                .map(records -> {
//                    IOrder order = null;
//                    for (Record record : records) {
//                        if (order == null) {
//                            order = createOrderFromRecord(record);
//                            order.setUser(createUserFromRecord(record));
//                            order.setItemList(new ArrayList<>());
//                        }
//                        order.getItemList().add(createMenuItemFromRecord(record));
//                    }
//                    return order;
//                })
//                .toList();
//    }
//
//
//    public IOrder getOrderById(Long orderId) {
//
//        if (orderId == null) throw new IllegalArgumentException("orderId cannot be null");
//
//        return dslContext.select(
//                        USERS.ID.as("user_id"),
//                        USERS.NAME.as("user_name"),
//                        USERS.EMAIL,
//                        USERS.PASSWORD,
//                        USERS.TELEGRAM,
//                        USERS.PHONE,
//                        USERS.ADDRESS,
//                        ORDERS_MENU_ITEMS.MENU_ITEM_ID,
//                        MENU_ITEMS.NAME.as("menu_item_name"),
//                        MENU_ITEMS.MENU_SECTION,
//                        MENU_ITEMS.PRICE,
//                        ORDERS_MENU_ITEMS.ORDER_ID,
//                        ORDERS.DATE_TIME,
//                        ORDERS.STATUS,
//                        ORDERS.TOTAL_PRICE)
//                .from(ORDERS)
//                .join(USERS).on(ORDERS.USER_ID.eq(USERS.ID))
//                .join(ORDERS_MENU_ITEMS).on(ORDERS.ID.eq(ORDERS_MENU_ITEMS.ORDER_ID))
//                .join(MENU_ITEMS).on(MENU_ITEMS.ID.eq(ORDERS_MENU_ITEMS.MENU_ITEM_ID))
//                .where(ORDERS.ID.eq(orderId))
//                .orderBy(ORDERS.ID)
//                .fetchGroups(ORDERS_MENU_ITEMS.ORDER_ID)
//                .values()
//                .stream()
//                .map(records -> {
//                    IOrder order = null;
//                    for (Record record : records) {
//                        if (order == null) {
//                            order = createOrderFromRecord(record);
//                            order.setUser(createUserFromRecord(record));
//                            order.setItemList(new ArrayList<>());
//                        }
//                        order.getItemList().add(createMenuItemFromRecord(record));
//                    }
//                    return order;
//                })
//                .findFirst().orElse(null);
//
//    }
//
//    private IMenuItem createMenuItemFromRecord(Record record) {
//        MenuItem menuItem = new MenuItem();
//        menuItem.setId(record.get(ORDERS_MENU_ITEMS.MENU_ITEM_ID));
//        menuItem.setName(record.get("menu_item_name", String.class));
//        menuItem.setMenuSection(MenuSection.valueOf(record.get(MENU_ITEMS.MENU_SECTION)));
//        menuItem.setPrice(record.get(MENU_ITEMS.PRICE));
//        return menuItem;
//    }
//
//
//    private IUser createUserFromRecord(Record record) {
//        IUser user = new User();
//        user.setId(record.get("user_id", Long.class));
//        user.setName(record.get("user_name", String.class));
//        user.setEmail(record.get(USERS.EMAIL));
//        user.setPassword(record.get(USERS.PASSWORD));
//        user.setTelegram(record.get(USERS.TELEGRAM));
//        user.setPhone(record.get(USERS.PHONE));
//        user.setAddress(record.get(USERS.ADDRESS));
//        return user;
//    }
//
//    private IOrder createOrderFromRecord(Record record) {
//        IOrder order = new Order();
//        order.setId(record.get("order_id", Long.class));
//        order.setDateTime(record.get(ORDERS.DATE_TIME));
//        order.setStatus(OrderStatus.valueOf(record.get(ORDERS.STATUS)));
//        order.setTotalPrice(record.get(ORDERS.TOTAL_PRICE));
//        return order;
//    }
//}
