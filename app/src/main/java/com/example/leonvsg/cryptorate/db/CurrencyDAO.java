package com.example.leonvsg.cryptorate.db;

import com.example.leonvsg.cryptorate.models.CurrencyModel;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by leonvsg on 24.08.17.
 */

public class CurrencyDAO extends BaseDaoImpl<CurrencyModel, Integer> {

    protected CurrencyDAO(ConnectionSource connectionSource,
                          Class<CurrencyModel> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<CurrencyModel> getAllCurrencies() throws SQLException{
        return this.queryForAll();
    }

}
