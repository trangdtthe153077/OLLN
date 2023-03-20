   public int getRevenuesByCategory(int a, String cate) {
        int total = 0;
        try {
            String sql = "select *from(select ROW_NUMBER() OVER (ORDER BY registered_date desc) \n"
                    + "as row_num,d.t, d.registered_date from\n"
                    + "(select  top 14 Sum(sale_price) as t, registered_date from\n"
                    + "(select registered_date, sale_price from Registrations r\n"
                    + "right join Price_Package pp on r.price_id = pp.id  inner join Course c\n"
                    + "on r.course_id = c.id inner join Category_SubCategory_Course csc \n"
                    + "on csc.category_id = c.category_id inner join Category_Course cc\n"
                    + "on cc.id = csc.subcategory_id  where cc.name = ?) as total \n"
                    + "group by registered_date ORDER BY registered_date desc ) as d ) as too where row_num = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, cate);
            statement.setInt(2, a);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                total = rs.getInt(2);
            }

        } catch (SQLException e) {
        }
        return total;

    }