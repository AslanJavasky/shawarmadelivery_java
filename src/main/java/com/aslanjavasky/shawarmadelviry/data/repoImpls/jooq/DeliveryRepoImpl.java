//package com.aslanjavasky.shawarmadelviry.data.repoImpls.jooq;
//
//import com.aslanjavasky.shawarmadelviry.domain.model.*;
//import com.aslanjavasky.shawarmadelviry.domain.repo.DeliveryRepo;
//import org.jooq.DSLContext;
//import org.springframework.dao.DataAccessException;
//import org.springframework.jdbc.core.ResultSetExtractor;
//import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
//import org.springframework.stereotype.Repository;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import org.jooq.Record;
//
//
//import static com.aslanjavasky.shawarmadelviry.generated.jooq.Tables.*;
//import static com.aslanjavasky.shawarmadelviry.generated.jooq.tables.Deliveries.DELIVERIES;
//
//@Repository("DRwJOOQ")
//public class DeliveryRepoImpl implements DeliveryRepo {
//
//    private final DSLContext dslContext;
//
//    public DeliveryRepoImpl(DSLContext dslContext) {
//        this.dslContext = dslContext;
//    }
//
//    @Override
//    public IDelivery saveDelivery(IDelivery delivery) {
//
//        if (delivery == null) throw new IllegalArgumentException("Delivery cannot be null");
//
//        Long deliveryId = dslContext.insertInto(DELIVERIES)
//                .set(DELIVERIES.ADDRESS, delivery.getAddress())
//                .set(DELIVERIES.PHONE, delivery.getPhone())
//                .set(DELIVERIES.DATE_TIME, delivery.getDateTime())
//                .set(DELIVERIES.ORDER_ID, delivery.getOrder().getId())
//                .returningResult(DELIVERIES.ID)
//                .fetchOne()
//                .get(DELIVERIES.ID);
//        if (deliveryId == null) throw new RuntimeException("Failed to save delivery, no generated key");
//        delivery.setId(deliveryId);
//
//        return delivery;
//    }
//
//    @Override
//    public IDelivery updateDelivery(IDelivery delivery) {
//
//        if (delivery == null) throw new IllegalArgumentException("Delivery cannot be null");
//
//        int affectedRow =dslContext.update(DELIVERIES)
//                .set(DELIVERIES.ADDRESS, delivery.getAddress())
//                .set(DELIVERIES.PHONE, delivery.getPhone())
//                .set(DELIVERIES.DATE_TIME, delivery.getDateTime())
//                .set(DELIVERIES.ORDER_ID, delivery.getOrder().getId())
//                .where(DELIVERIES.ID.eq(delivery.getId()))
//                .execute();
//
//        if (affectedRow == 0) throw new RuntimeException("Failed to update delivery, no rows affected");
//        return delivery;
//    }
//
//    @Override
//    public IDelivery getDeliveryById(Long id) {
//
//        if (id == null) throw new IllegalArgumentException("id cannot be null");
//
//        return dslContext.select(
//                DELIVERIES.ID.as("delivery_id"),
//                DELIVERIES.DATE_TIME.as("delivery_date_time"),
//                DELIVERIES.ORDER_ID,
//                ORDERS.DATE_TIME.as("order_date_time"),
//                ORDERS.STATUS,
//                ORDERS.USER_ID,
//                ORDERS.TOTAL_PRICE,
//                USERS.NAME.as("user_name"),
//                USERS.EMAIL,
//                USERS.PASSWORD,
//                USERS.TELEGRAM,
//                USERS.PHONE,
//                USERS.ADDRESS,
//                USERS.EMAIL,
//                ORDERS_MENU_ITEMS.MENU_ITEM_ID,
//                MENU_ITEMS.NAME.as("menu_item_name"),
//                MENU_ITEMS.MENU_SECTION,
//                MENU_ITEMS.PRICE)
//                .from(DELIVERIES)
//                .join(ORDERS).on(DELIVERIES.ORDER_ID.eq(ORDERS.ID))
//                .join(USERS).on(ORDERS.USER_ID.eq(USERS.ID))
//                .join(ORDERS_MENU_ITEMS).on(ORDERS.ID.eq(ORDERS_MENU_ITEMS.ORDER_ID))
//                .join(MENU_ITEMS).on(ORDERS_MENU_ITEMS.MENU_ITEM_ID.eq(MENU_ITEMS.ID))
//                .where(DELIVERIES.ID.eq(id))
//                .orderBy(DELIVERIES.ID)
//                .fetchGroups(DELIVERIES.ID)
//                .values()
//                .stream()
//                .map(records ->{
//                    IDelivery delivery = null;
//                    for(Record record:records) {
//                        if (delivery == null){
//                            delivery = createDeliveryFromRecord(record);
//                            delivery.setOrder(createOrderFromRecord(record));
//                            delivery.getOrder().setUser(createUserFromRecord(record));
//                            delivery.getOrder().setItemList(new ArrayList<>());
//                        }
//                        delivery.getOrder().getItemList().add(createMenuItemFromRecord(record));
//                    }
//                    return delivery;
//                })
//                .findFirst().orElse(null);
//
//    }
//
//    private IMenuItem createMenuItemFromRecord(Record record) {
//        MenuItem menuItem = new MenuItem();
//        menuItem.setId(record.get(ORDERS_MENU_ITEMS.MENU_ITEM_ID));
//        menuItem.setName(record.get("menu_item_name",String.class));
//        menuItem.setMenuSection(MenuSection.valueOf(record.get(MENU_ITEMS.MENU_SECTION)));
//        menuItem.setPrice(record.get(MENU_ITEMS.PRICE));
//        return menuItem;
//
//    }
//
//    private IUser createUserFromRecord(Record record) {
//        IUser user = new User();
//        user.setId(record.get(ORDERS.USER_ID));
//        user.setName(record.get("user_name",String.class));
//        user.setEmail(record.get(USERS.EMAIL));
//        user.setPassword(record.get(USERS.PASSWORD));
//        user.setTelegram(record.get(USERS.TELEGRAM));
//        user.setPhone(record.get(USERS.PHONE));
//        user.setAddress(record.get(USERS.ADDRESS));
//        return user;
//
//    }
//
//    private IOrder createOrderFromRecord(Record record) {
//        IOrder newOrder = new Order();
//        newOrder.setId(record.get("order_id",Long.class));
//        newOrder.setDateTime(record.get("order_date_time", LocalDateTime.class));
//        newOrder.setStatus(OrderStatus.valueOf(record.get(ORDERS.STATUS)));
//        newOrder.setTotalPrice(record.get(ORDERS.TOTAL_PRICE));
//        return newOrder;
//    }
//
//    private IDelivery createDeliveryFromRecord(Record record) {
//        IDelivery delivery = new Delivery();
//        delivery.setId(record.get("delivery_id", Long.class));
//        delivery.setAddress(record.get(DELIVERIES.ADDRESS));
//        delivery.setPhone(record.get(DELIVERIES.PHONE));
//        delivery.setDateTime(record.get("delivery_date_time", LocalDateTime.class));
//        return delivery;
//
//    }
//
//
//
//
//
//
//
//}
