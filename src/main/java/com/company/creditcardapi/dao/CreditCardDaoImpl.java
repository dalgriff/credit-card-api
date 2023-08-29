package com.company.creditcardapi.dao;

import com.company.creditcardapi.models.CreditCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CreditCardDaoImpl implements CreditCardDao {

    private JdbcTemplate jdbcTemplate;

    private static final String INSERT_CREDIT_CARD_SQL =
            "INSERT INTO credit_cards (card_number, expiration_date, current_balance, credit_limit, apr, user_id) VALUES (?, ?, ?, ?, ?, ?)";

    private static final String FIND_BY_ID_SQL =
            "SELECT * FROM credit_cards WHERE id = ?";

    private static final String UPDATE_CREDIT_CARD_SQL =
            "UPDATE credit_cards SET card_number = ?, expiration_date = ?, current_balance = ?, credit_limit = ?, apr = ?, user_id = ? WHERE id = ?";

    private static final String DELETE_CARD_SQL =
            "DELETE FROM credit_cards WHERE id = ?";

    private static final String FIND_ALL_SQL =
            "SELECT * FROM credit_cards";

    private static final String FIND_BY_USER_SQL =
            "SELECT * FROM credit_cards WHERE user_id = ?";


    @Autowired
    public CreditCardDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public CreditCard addCreditCard(CreditCard creditCard) {
        jdbcTemplate.update(
                INSERT_CREDIT_CARD_SQL,
                creditCard.getCardNumber(),
                creditCard.getExpirationDate(),
                creditCard.getCurrentBalance(),
                creditCard.getCreditLimit(),
                creditCard.getApr(),
                creditCard.getUserId()
        );

        int id = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);

        creditCard.setId(id);

        return creditCard;
    }

    @Override
    public CreditCard getCreditCard(Integer id) {
        try {
            return jdbcTemplate.queryForObject(FIND_BY_ID_SQL, this::mapRowToCard, id);
        } catch (EmptyResultDataAccessException e) {
            // if there is no match for this credit card id, return null
            return null;
        }
    }

    @Override
    public void updateCreditCard(CreditCard creditCard) {
        jdbcTemplate.update(
                UPDATE_CREDIT_CARD_SQL,
                creditCard.getCardNumber(),
                creditCard.getExpirationDate(),
                creditCard.getCurrentBalance(),
                creditCard.getCreditLimit(),
                creditCard.getApr(),
                creditCard.getUserId(),
                creditCard.getId()
        );
    }

    @Override
    public List<CreditCard> getCreditCardsByUser(Integer userId) {
        return jdbcTemplate.query(FIND_BY_USER_SQL, this::mapRowToCard, userId);
    }

    @Override
    public void deleteCreditCard(Integer id) {
        jdbcTemplate.update(DELETE_CARD_SQL, id);
    }

    @Override
    public List<CreditCard> findAll() {
        return jdbcTemplate.query(FIND_ALL_SQL, this::mapRowToCard);
    }

    private CreditCard mapRowToCard(ResultSet rs, int rowNum) throws SQLException {
        CreditCard creditCard = new CreditCard();
        creditCard.setId(rs.getInt("id"));
        creditCard.setCurrentBalance(rs.getBigDecimal("current_balance"));
        creditCard.setExpirationDate(rs.getDate("expiration_date").toLocalDate());
        creditCard.setCreditLimit(rs.getBigDecimal("credit_limit"));
        creditCard.setCardNumber(rs.getString("card_number"));
        creditCard.setApr(rs.getBigDecimal("apr"));
        creditCard.setUserId(rs.getObject("user_id", Integer.class));

        return creditCard;
    }
}
