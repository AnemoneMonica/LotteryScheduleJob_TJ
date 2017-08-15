package comm.hb.lottery.conn;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hb.lottery.conf.Configuration;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DBConnectionSql {

	private static Logger logger = LoggerFactory.getLogger(DBConnectionSql.class);
	//private static final ThreadLocal<Connection> connLocal = new ThreadLocal<Connection>();
	public static DataSource ds;
	
	public static DataSource getInstence() {
		HikariConfig config = new HikariConfig();
		config.setDriverClassName(Configuration.getGlobalMsg("jdbc.driver"));
		config.setJdbcUrl(Configuration.getGlobalMsg("jdbc.url"));
		config.setUsername(Configuration.getGlobalMsg("jdbc.username"));
		config.setPassword(Configuration.getGlobalMsg("jdbc.password"));
		config.setAutoCommit(true);
		// 池中�?小空闲链接数�?
		config.setMinimumIdle(Integer.parseInt(Configuration.getGlobalMsg("jdbc.maxidle")));
		// 池中�?大链接数�?
		config.setMaximumPoolSize(Integer.parseInt(Configuration.getGlobalMsg("jdbc.maxactive")));
		try {
			ds = new HikariDataSource(config);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ds;
	}

	/**
	 * 获取连接
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		if (null==ds){
			return  getInstence().getConnection();
		}else{
			return ds.getConnection();
		}

	}

}
