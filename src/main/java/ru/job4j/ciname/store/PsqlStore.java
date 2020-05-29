package ru.job4j.ciname.store;

import org.apache.commons.dbcp2.BasicDataSource;
import ru.job4j.ciname.models.Account;
import ru.job4j.ciname.models.Hall;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store {

    private final BasicDataSource pool = new BasicDataSource();

    private PsqlStore() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(
                new FileReader("db.properties")
        )) {
            cfg.load(io);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
        pool.setDefaultAutoCommit(false);
    }

    private static final class Lazy {
        private static final Store INST = new PsqlStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    @Override
    public List<Hall> findAllPlaces() {
        List<Hall> halls = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM hall")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    halls.add(new Hall(it.getInt("id"), it.getInt("line"), it.getInt("col"), it.getBoolean("busy")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return halls;

    }

    @Override
    public Integer createAccount(Account account, Integer line, Integer col) {
        Integer result = null;
        try (
                Connection cn = pool.getConnection();
                PreparedStatement accountPs =  cn.prepareStatement("INSERT INTO account(hall_id, amount, phone, fullname) VALUES (?, ?, ?, ?) ON CONFLICT (hall_id) DO NOTHING;", PreparedStatement.RETURN_GENERATED_KEYS);
                PreparedStatement hallPs =  cn.prepareStatement("UPDATE hall set busy='1' WHERE line=? AND col=?");
        ) {
            accountPs.setInt(1, account.getHallId());
            accountPs.setDouble(2, account.getAmount());
            accountPs.setString(3, account.getPhone());
            accountPs.setString(4, account.getFullname());
            accountPs.executeUpdate();
            try (ResultSet id = accountPs.getGeneratedKeys()) {
                if (id.next()) {
                    account.setId(id.getInt(1));
                }
            }
            if (account.getId() == null) {
                return result;
            }
            result = account.getId();
            hallPs.setInt(1, line);
            hallPs.setInt(2, col);
            hallPs.executeUpdate();
            cn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Hall findPlaceByLineAndRow(int line, int col) {
        List<Hall> halls = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM hall WHERE line=? AND col=?")
        ) {
            ps.setInt(1, line);
            ps.setInt(2, col);
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    halls.add(new Hall(it.getInt("id"), it.getInt("line"), it.getInt("col"), it.getBoolean("busy")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return halls.get(0);
    }
}
