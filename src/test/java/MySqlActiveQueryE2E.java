/**
 * File MySqlActiveQueryE2E
 *
 * Author d.a.ganzha
 * Project parser-java
 * Created by 23.02.19 11:23
 */

import activequery.ActiveQuery;
import activequery.adapters.MysqlQueryBuilder;
import activequery.conditions.*;
import models.User;
import models.UserRole;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static activequery.aggregations.All.ALL;
import static activequery.aggregations.Avg.AVG;
import static activequery.aggregations.Count.COUNT;
import static activequery.aggregations.Max.MAX;
import static activequery.aggregations.Min.MIN;
import static activequery.aggregations.Sum.SUM;
import static activequery.conditions.Condition.*;

/**
 * Class MySqlActiveQueryE2E
 *
 * Author d.a.ganzha
 * Project parser-java
 * Package ru.returnt.parser
 * Created by 23.02.19 11:23
 */
public class MySqlActiveQueryE2E {

    private static final String SELECT = "SELECT COUNT(`users`.`id`) AS `countId`, `users`.`id` AS `ID`, AVG(`users_roles`.`users_id`) AS `roleId`, MAX(`users`.`id`) AS `ID`, `id`, `email`, `users`.`*`, SUM(`users`.`id`) AS `ID`, MIN(`users`.`id`) AS `ID` FROM `users`";
    private static final String FROM = "SELECT `users`.`*` FROM `users`, `users_roles`";
    private static final String WHERE = "SELECT `users`.`id` AS `ID` FROM `users` WHERE `users`.`id` %s ?";
    private static final String WHERE_AND = "SELECT `users`.`id` AS `ID` FROM `users` WHERE `users`.`id` = `users`.`id` AND `users`.`id` = `users`.`id`";
    private static final String WHERE_OR = "SELECT `users`.`id` AS `ID` FROM `users` WHERE `users`.`id` = `users`.`id` OR `users`.`id` = `users`.`id`";
    private static final String WHERE_AND_NOT = "SELECT `users`.`id` AS `ID` FROM `users` WHERE `users`.`id` = `users`.`id` AND NOT `users`.`id` = `users`.`id`";
    private static final String WHERE_AND_GROUP = "SELECT `users`.`id` AS `ID` FROM `users` WHERE `users`.`id` = `users`.`id` AND ( `users`.`id` = `users`.`id` OR `users`.`id` = `users`.`id` )";
    private static final String WHERE_OR_GROUP = "SELECT `users`.`id` AS `ID` FROM `users` WHERE `users`.`id` = `users`.`id` OR ( `users`.`id` = `users`.`id` OR `users`.`id` = `users`.`id` )";
    private static final String WHERE_IN = "SELECT `users`.`id` AS `ID` FROM `users` WHERE `users`.`id` IN (?, ?, ?)";
    private static final String WHERE_BETWEEN = "SELECT `users`.`id` AS `ID` FROM `users` WHERE `users`.`id` BETWEEN ? AND ?";
    private static final String WHERE_LIKE = "SELECT `users`.`id` AS `ID` FROM `users` WHERE `users`.`id` LIKE ?";
    private static final String CROSS_JOIN = "SELECT `users`.`id` AS `ID` FROM `users`, `users_roles` WHERE `users`.`id` LIKE ? AND `users_roles`.`users_id` = `users`.`id`";
    private static final String INNER_JOIN = "SELECT `users`.`id` AS `ID` FROM `users` INNER JOIN `users_roles` ON `users_roles`.`users_id` = `users`.`id` OR `users_roles`.`users_id` = ?";
    private static final String INNER_JOIN_2 = "SELECT `users`.`id` AS `ID` FROM `users` INNER JOIN `users_roles` ON `users_roles`.`users_id` = `users`.`id`";
    private static final String LEFT_JOIN = "SELECT `users`.`id` AS `ID` FROM `users` LEFT JOIN `users_roles` ON `users_roles`.`users_id` = `users`.`id` OR `users_roles`.`users_id` = ?";
    private static final String LEFT_JOIN_2 = "SELECT `users`.`id` AS `ID` FROM `users` LEFT JOIN `users_roles` ON `users_roles`.`users_id` = `users`.`id`";
    private static final String RIGHT_JOIN = "SELECT `users`.`id` AS `ID` FROM `users` RIGHT JOIN `users_roles` ON `users_roles`.`users_id` = `users`.`id` OR `users_roles`.`users_id` = ?";
    private static final String RIGHT_JOIN_2 = "SELECT `users`.`id` AS `ID` FROM `users` RIGHT JOIN `users_roles` ON `users_roles`.`users_id` = `users`.`id`";
    private static final String GROUP_BY = "SELECT `users`.`id` AS `ID` FROM `users` GROUP BY `users`.`id`, `users_roles`.`users_id`";
    private static final String ORDER_BY = "SELECT `users`.`id` AS `ID` FROM `users` ORDER BY COUNT(`users_roles`.`users_id`) DESC, `users`.`id` ASC";
    private static final String LIMIT = "SELECT `users`.`id` AS `ID` FROM `users` LIMIT 10";
    private static final String OFFSET = "SELECT `users`.`id` AS `ID` FROM `users` OFFSET 10";

    @Test
    public void select() {
        final String query = getActiveQuery()
            .from(User.class)
            .select(
                ALL(User.class),
                User.Fields.id, User.Fields.email,
                COUNT(User.Fields.id).as("countId"),
                AVG(UserRole.userId),
                MAX(User.Fields.id),
                MIN(User.Fields.id),
                SUM(User.Fields.id),
                () -> "id"
            )
            .build()
            .getQuery()
            .toString();
        Assert.assertEquals(SELECT, query);
    }

    @Test
    public void from() {
        final String query = getActiveQuery()
            .from(User.class, UserRole.class)
            .select(ALL(User.class))
            .build()
            .getQuery()
            .toString();
        Assert.assertEquals(FROM, query);
    }

    @Test
    public void where() {
        Assert.assertEquals(String.format(WHERE, "="), prepareWhereCondition(EQ));
        Assert.assertEquals(String.format(WHERE, "<>"), prepareWhereCondition(NEQ));
        Assert.assertEquals(String.format(WHERE, ">"), prepareWhereCondition(GT));
        Assert.assertEquals(String.format(WHERE, "<"), prepareWhereCondition(LT));
        Assert.assertEquals(String.format(WHERE, ">="), prepareWhereCondition(GTE));
        Assert.assertEquals(String.format(WHERE, "<="), prepareWhereCondition(LTE));
    }

    @Test
    public void whereAnd() {
        final String query = getActiveQuery()
            .from(User.class)
            .select(User.Fields.id)
            .where(func ->
                func.add(User.Fields.id.eq(User.Fields.id))
                    .and((new WhereFunc.Where() {
                        @Override
                        public Field l() {
                            return User.Fields.id;
                        }

                        @Override
                        public Condition operator() {
                            return EQ;
                        }

                        @Override
                        public Field r() {
                            return User.Fields.id;
                        }
                    })))
            .build()
            .getQuery()
            .toString();
        Assert.assertEquals(WHERE_AND, query);

        final String query2 = getActiveQuery()
            .from(User.class)
            .select(User.Fields.id)
            .where(User.Fields.id.eq(User.Fields.id), User.Fields.id.eq(User.Fields.id))
            .build()
            .getQuery()
            .toString();
        Assert.assertEquals(WHERE_AND, query2);

        final String query3 = getActiveQuery()
            .from(User.class)
            .select(User.Fields.id)
            .where(User.Fields.id.eq(User.Fields.id))
            .where(User.Fields.id.eq(User.Fields.id))
            .build()
            .getQuery()
            .toString();
        Assert.assertEquals(WHERE_AND, query3);
    }

    @Test
    public void whereOr() {
        final String query = getActiveQuery()
            .from(User.class)
            .select(User.Fields.id)
            .where(func ->
                func.add(User.Fields.id.eq(User.Fields.id))
                    .or(User.Fields.id.eq(User.Fields.id)))
            .build()
            .getQuery()
            .toString();
        Assert.assertEquals(WHERE_OR, query);

        final String query2 = getActiveQuery()
            .from(User.class)
            .select(User.Fields.id)
            .where(User.Fields.id.eq(User.Fields.id))
            .orWhere(User.Fields.id.eq(User.Fields.id))
            .build()
            .getQuery()
            .toString();
        Assert.assertEquals(WHERE_OR, query2);
    }

    @Test
    public void whereAndNot() {
        final String query = getActiveQuery()
            .from(User.class)
            .select(User.Fields.id)
            .where(func ->
                func.add(User.Fields.id.eq(User.Fields.id))
                    .andNot(User.Fields.id.eq(User.Fields.id)))
            .build()
            .getQuery()
            .toString();
        Assert.assertEquals(WHERE_AND_NOT, query);
    }

    @Test
    public void whereAndGroup() {
        final String query = getActiveQuery()
            .from(User.class)
            .select(User.Fields.id)
            .where(func ->
                func.add(User.Fields.id.eq(User.Fields.id))
                    .and(new WhereGroup()
                        .add(User.Fields.id.eq(User.Fields.id))
                        .or(User.Fields.id.eq(User.Fields.id)))
            )
            .build()
            .getQuery()
            .toString();
        Assert.assertEquals(WHERE_AND_GROUP, query);
    }

    @Test
    public void whereOrGroup() {
        final String query = getActiveQuery()
            .from(User.class)
            .select(User.Fields.id)
            .where(func ->
                func.add(User.Fields.id.eq(User.Fields.id))
                    .or(new WhereGroup()
                        .add(User.Fields.id.eq(User.Fields.id))
                        .or(User.Fields.id.eq(User.Fields.id)))
            )
            .build()
            .getQuery()
            .toString();
        Assert.assertEquals(WHERE_OR_GROUP, query);
    }

    @Test
    public void whereIn() {
        final MysqlQueryBuilder query = getActiveQuery()
            .from(User.class)
            .select(User.Fields.id)
            .where(func -> func.add(User.Fields.id.in(Arrays.asList(10, 20, 30))))
            .build();
        Assert.assertEquals(WHERE_IN, query.getQuery().toString());
        Assert.assertEquals(10, query.getQueryArgs().get(0));
        Assert.assertEquals(20, query.getQueryArgs().get(1));
        Assert.assertEquals(30, query.getQueryArgs().get(2));
    }

    @Test
    public void whereBetween() {
        final MysqlQueryBuilder query = getActiveQuery()
            .from(User.class)
            .select(User.Fields.id)
            .where(func -> func.add(User.Fields.id.between(Arrays.asList("2018-12", "2019-12"))))
            .build();
        Assert.assertEquals(WHERE_BETWEEN, query.getQuery().toString());
        Assert.assertEquals("2018-12", query.getQueryArgs().get(0));
        Assert.assertEquals("2019-12", query.getQueryArgs().get(1));
    }

    @Test
    public void whereLike() {
        final MysqlQueryBuilder query = getActiveQuery()
            .from(User.class)
            .select(User.Fields.id)
            .where(User.Fields.id.like("%name%"))
            .build();
        Assert.assertEquals(WHERE_LIKE, query.getQuery().toString());
        Assert.assertEquals("%name%", query.getQueryArgs().get(0));
    }

    @Test
    public void crossJoin() {
        final ActiveQuery<MysqlQueryBuilder> activeQuery = getActiveQuery();
        final MysqlQueryBuilder query = activeQuery
            .from(User.class, UserRole.class)
            .select(User.Fields.id)
            .where(func -> func.add(new WhereFunc.Where() {
                @Override
                public Field l() {
                    return User.Fields.id;
                }

                @Override
                public Condition operator() {
                    return LIKE;
                }

                @Override
                public String r() {
                    return "%name%";
                }
            }).and(new WhereFunc.Where() {
                @Override
                public Object l() {
                    return UserRole.userId;
                }

                @Override
                public Condition operator() {
                    return EQ;
                }

                @Override
                public Object r() {
                    return User.Fields.id;
                }
            }))
            .build();
        Assert.assertEquals(CROSS_JOIN, query.getQuery().toString());
        Assert.assertEquals("%name%", query.getQueryArgs().get(0));
    }

    @Test
    public void innerJoin() {
        final ActiveQuery<MysqlQueryBuilder> activeQuery = getActiveQuery();
        final MysqlQueryBuilder query = activeQuery
            .from(User.class)
            .select(User.Fields.id)
            .innerJoin(prepareJoinCondition())
            .build();
        Assert.assertEquals(INNER_JOIN, query.getQuery().toString());
        Assert.assertEquals(10, query.getQueryArgs().get(0));

        final ActiveQuery<MysqlQueryBuilder> activeQuery2 = getActiveQuery();
        final MysqlQueryBuilder query2 = activeQuery2
            .from(User.class)
            .select(User.Fields.id)
            .innerJoin(UserRole.class, UserRole.userId.eq(User.Fields.id))
            .build();
        Assert.assertEquals(INNER_JOIN_2, query2.getQuery().toString());
    }

    @Test
    public void leftJoin() {
        final ActiveQuery<MysqlQueryBuilder> activeQuery = getActiveQuery();
        final MysqlQueryBuilder query = activeQuery
            .from(User.class)
            .select(User.Fields.id)
            .leftJoin(prepareJoinCondition())
            .build();
        Assert.assertEquals(LEFT_JOIN, query.getQuery().toString());
        Assert.assertEquals(10, query.getQueryArgs().get(0));

        final ActiveQuery<MysqlQueryBuilder> activeQuery2 = getActiveQuery();
        final MysqlQueryBuilder query2 = activeQuery2
            .from(User.class)
            .select(User.Fields.id)
            .leftJoin(UserRole.class, UserRole.userId.eq(User.Fields.id))
            .build();
        Assert.assertEquals(LEFT_JOIN_2, query2.getQuery().toString());
    }

    @Test
    public void rightJoin() {
        final ActiveQuery<MysqlQueryBuilder> activeQuery = getActiveQuery();
        final MysqlQueryBuilder query = activeQuery
            .from(User.class)
            .select(User.Fields.id)
            .rightJoin(prepareJoinCondition())
            .build();
        Assert.assertEquals(RIGHT_JOIN, query.getQuery().toString());
        Assert.assertEquals(10, query.getQueryArgs().get(0));

        final ActiveQuery<MysqlQueryBuilder> activeQuery2 = getActiveQuery();
        final MysqlQueryBuilder query2 = activeQuery2
            .from(User.class)
            .select(User.Fields.id)
            .rightJoin(UserRole.class, UserRole.userId.eq(User.Fields.id))
            .build();
        Assert.assertEquals(RIGHT_JOIN_2, query2.getQuery().toString());
    }

    @Test
    public void groupBy() {
        final String query = getActiveQuery()
            .from(User.class)
            .select(User.Fields.id)
            .groupBy(User.Fields.id, UserRole.userId)
            .build()
            .getQuery()
            .toString();
        Assert.assertEquals(GROUP_BY, query);
    }

    @Test
    public void orderBy() {
        final String query = getActiveQuery()
            .from(User.class)
            .select(User.Fields.id)
            .orderBy(new OrderBy() {
                @Override
                public Field field() {
                    return User.Fields.id;
                }

                @Override
                public OrderDirection direction() {
                    return OrderDirection.ASC;
                }
            }, new OrderBy() {
                @Override
                public Field field() {
                    return COUNT(UserRole.userId);
                }

                @Override
                public OrderDirection direction() {
                    return OrderDirection.DESC;
                }
            })
            .build()
            .getQuery()
            .toString();
        Assert.assertEquals(ORDER_BY, query);
    }

    @Test
    public void limit() {
        final String query = getActiveQuery()
            .from(User.class)
            .select(User.Fields.id)
            .limit(10L)
            .build()
            .getQuery()
            .toString();
        Assert.assertEquals(LIMIT, query);
    }

    @Test
    public void offset() {
        final MysqlQueryBuilder query = getActiveQuery()
            .from(User.class)
            .select(User.Fields.id)
            .offset(10L)
            .build();
        Assert.assertEquals(OFFSET, query.getQuery().toString());
    }

    private Join prepareJoinCondition() {
        return new Join() {
            @Override
            public Class table() {
                return UserRole.class;
            }

            @Override
            public void on(WhereGroup whereGroup) {
                whereGroup
                    .add(UserRole.userId.eq(User.Fields.id))
                    .or(new WhereFunc.Where() {
                        @Override
                        public Object l() {
                            return UserRole.userId;
                        }

                        @Override
                        public Condition operator() {
                            return EQ;
                        }

                        @Override
                        public Object r() {
                            return 10;
                        }
                    });
            }
        };
    }

    private String prepareWhereCondition(final Condition condition) {
        return getActiveQuery()
            .from(User.class)
            .select(User.Fields.id)
            .where(func -> func.add(new WhereFunc.Where() {
                @Override
                public Field l() {
                    return User.Fields.id;
                }

                @Override
                public Condition operator() {
                    return condition;
                }

                @Override
                public Integer r() {
                    return 123;
                }
            }))
            .build()
            .getQuery()
            .toString();
    }

    private ActiveQuery<MysqlQueryBuilder> getActiveQuery() {
        return new ActiveQuery<>(new MysqlQueryBuilder());
    }

}
