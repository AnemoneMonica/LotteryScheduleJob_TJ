package comm.hb.lottery.conn;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueryHelper {

	  private static Logger logger = LoggerFactory.getLogger(QueryHelper.class);
	    private Connection dbConnection = null;

	    public QueryHelper(){
	        try {
	          //  dbConnection = SqlServConnection.getConnection();
	        	dbConnection = DBConnectionSql.getConnection();
	        } catch (SQLException e) {
	            logger.error("获取数据库连接失败：{}",e.fillInStackTrace());
	        }
	    }

	    public void Close() {
	        CloseCon();
	    }


	    public void CloseCon() {
	        try {
	            if (dbConnection != null) {
	                dbConnection.close();
	            }
	        } catch (Exception ex) {
	        	System.out.println(ex);
	        }
	    }

	    private final static QueryRunner _g_runner = new QueryRunner(true);
	    private final static ColumnListHandler _g_columnListHandler = new ColumnListHandler(){
	        @Override
	        protected Object handleRow(ResultSet rs) throws SQLException {
	            Object obj = super.handleRow(rs);
	            if(obj instanceof BigInteger)
	                return ((BigInteger)obj).longValue();
	            return obj;
	        }

	    };
	    private final static ScalarHandler _g_scaleHandler = new ScalarHandler(){
	        @Override
	        public Object handle(ResultSet rs) throws SQLException {
	            Object obj = super.handle(rs);
	            if(obj instanceof BigInteger)
	                return ((BigInteger)obj).longValue();
	            return obj;
	        }
	    };

	    private final static List<Class<?>> PrimitiveClasses = new ArrayList<Class<?>>(){{
	        add(Long.class);
	        add(Integer.class);
	        add(String.class);
	        add(java.util.Date.class);
	        add(java.sql.Date.class);
	        add(java.sql.Timestamp.class);
	    }};

	    private final static boolean _IsPrimitive(Class<?> cls) {
	        return cls.isPrimitive() || PrimitiveClasses.contains(cls) ;
	    }
	    public List<Map<String, Object>> query(String sql) {

	        try {
				List<Map<String, Object>> map=(List<Map<String, Object>>)_g_runner.query(dbConnection, sql, new MapListHandler());
	           return map;
	        } catch (SQLException e) {
				logger.error("查询异常：{}",e.fillInStackTrace());
				return null;
	        } 
	    }


	    /**
	     * 读取某个对象
	     * @param sql
	     * @param params
	     * @return
	     * @throws DBException 
	     */
	    @SuppressWarnings("rawtypes")
	    public static <T> T read(Class<T> beanClass, String sql, Object... params) {
	        QueryHelper queryHelper=new QueryHelper();
	        try{
	            return (T)_g_runner.query(queryHelper.dbConnection, sql, _IsPrimitive(beanClass)?_g_scaleHandler:new BeanHandler(beanClass), params);
	        }catch(SQLException e){
	           return null;
	        }
	    }

	    /**
	     * 对象查询
	     * @param <T>
	     * @param beanClass
	     * @param sql
	     * @param params
	     * @return
	     * @throws DBException 
	     */
	    @SuppressWarnings("rawtypes")
	    public  <T> List<T> query(Class<T> beanClass, String sql) throws Exception {
	        try{
	            return (List<T>)_g_runner.query(dbConnection, sql, _IsPrimitive(beanClass)?_g_columnListHandler:new BeanListHandler(beanClass));
	        }catch(SQLException e){
	            throw new Exception(e);
	        }
	    }

	   
	 
	    /**
	     * 执行统计查询语句，语句的执行结果必须只返回一个数�?
	     * @param sql
	     * @param params
	     * @return
	     * @throws DBException 
	     */
	    public long stat(String sql, Object...params) throws Exception {
	        try{
	            Number num = (Number)_g_runner.query(dbConnection, sql, _g_scaleHandler, params);
	            return (num!=null)?num.longValue():-1;
	        }catch(SQLException e){
	            throw new Exception(e);
	        }
	    }

	    /**
	     * 执行查询语句，语句的执行结果必须只返回一个字符串
	     * @param sql
	     * @param params
	     * @return
	     * @throws DBException 
	     */
	    public String statString(String sql, Object...params) throws Exception {
	        try{
	            Object s = _g_runner.query(dbConnection, sql, _g_scaleHandler, params);
	            return (s!=null)?s.toString():"";
	        }catch(SQLException e){
	            throw new Exception(e);
	        }
	    }

	    /**
	     * 执行统计查询语句，语句的执行结果必须只返回一个数�?
	     * @param cache_region
	     * @param key
	     * @param sql
	     * @param params
	     * @return
	     */
//	    public static long stat_cache(String cache_region, Serializable key, String sql, Object...params) {
//	        Number value = (Number)CacheManager.get(cache_region, key);
//	        if(value == null){
//	            value = stat(sql, params);
//	            CacheManager.set(cache_region, key, value);
//	        }
//	        return value.longValue();
//	    }

	    /**
	     * 执行INSERT/UPDATE/DELETE语句
	     * @param sql
	     * @param params
	     * @return
	     * @throws DBException 
	     */
	    public  int update(String sql, Object...params) throws Exception {
	        try{
	            return _g_runner.update(dbConnection, sql, params);
	        }catch(SQLException e){
	            throw new Exception(e);
	        }
	    }

	    /**
	     * 批量执行指定的SQL语句
	     * 数据库最终会关闭
	     * @param sql
	     * @param params
	     * @return
	     * @throws DBException 
	     */
	    public  int[] batch(String sql, Object[][] params) throws Exception {
	        try{
	            return _g_runner.batch(dbConnection, sql, params);
	        }catch(SQLException e){
	            throw new Exception(e);
	        }
	    }

	    /**
	     * 查询返回单长整型数据
	     * @param sql
	     * @param params
	     * @return
	     */
	    public static long QueryLong(String sql, Object...params) {
	        QueryHelper queryHelper=new QueryHelper();
	        long l=0;
	        try{
	            l= queryHelper.stat(sql,params);
	        }catch (Exception e){
	            return 0;
	        }finally {
	            if (queryHelper.dbConnection!=null){
	                queryHelper.Close();
	            }
	        }
	        return l;
	    }

	    /**
	     * 执行查询语句，语句的执行结果必须只返回一个字符串
	     * @param sql
	     * @param params
	     * @return
	     */
	    public static String QueryString(String sql, Object...params) {
	        QueryHelper queryHelper=new QueryHelper();
	        String s="";
	        try{
	            s= queryHelper.statString(sql,params);
	        }catch (Exception e){
	            return "";
	        }finally {
	            if (queryHelper.dbConnection!=null){
	                queryHelper.Close();
	            }
	        }
	        return s;
	    }


	    /**
	     * 查询返回对象list
	     * @param beanClass
	     * @param sql
	     * @param params
	     * @param <T>
	     * @return
	     */
	    public static  <T> List<T> QueryList(Class<T> beanClass, String sql) {
	        QueryHelper queryHelper=new QueryHelper();
	        List<T> list=new ArrayList<T>();
	        try {
	            list = queryHelper.query(beanClass, sql);
	        }catch(Exception e){
	            return null;
	        }finally {
	            if (queryHelper.dbConnection!=null){
	                queryHelper.Close();
	            }
	        }
	        return list;
	    }

	    /**
	     * 执行INSERT/UPDATE/DELETE语句(增删�?)
	     * @param sql
	     * @param params
	     * @return
	     */
	    public static boolean Update(String sql, Object...params) {
	        QueryHelper queryHelper=new QueryHelper();
	        boolean flag=false;
	        try{
	            int  i=queryHelper.update(sql, params);
	            if (i>0){
	                flag=true;
	            }
	        }catch(Exception e){
	            return  flag;
	        }finally {
	            if (queryHelper.dbConnection!=null){
	                queryHelper.Close();
	            }
	        }
	        return  flag;
	    }


	    /**
	     * 批量执行指定的SQL语句
	     * @param sql
	     * @param params
	     * @return
	     */
	    public static  int[] Batch(String sql, Object[][] params) {
	        QueryHelper queryHelper = new QueryHelper();
	        int[] a={};
	        try {
	            a= queryHelper.batch(sql, params);
	            if (queryHelper.dbConnection != null) {
	                queryHelper.Close();
	            }
	        } catch (Exception e) {

	        } finally {
	            if (queryHelper.dbConnection != null) {
	                queryHelper.Close();
	            }
	        }
	        return  a;
	    }

	    @SuppressWarnings("unchecked")
		public <T> T query(String sql,Class<T> beanClass) {
	        
	        try {
	        	 return (T) new QueryRunner().query(dbConnection, sql,  _IsPrimitive(beanClass)?_g_scaleHandler:new BeanHandler(beanClass));
	        } catch (SQLException ex) {
	           ex.printStackTrace();
	            return null;
	        } 
	    }
	    public static <T> T Query(String sql,Class<T> beanClass) {
	    	QueryHelper queryHelper = new QueryHelper();
		        try {
		        	
		            return (T) queryHelper.query(sql,beanClass);
		        } catch (Exception ex) {
		           ex.printStackTrace();
		            return null;
		            
		        }  finally {
					if (queryHelper != null) queryHelper.Close();
				}
		    }
	    
	    public static List<Map<String, Object>> QueryListMap(String sql) {
	    	QueryHelper queryHelper = new QueryHelper();
			try {
				return queryHelper.query(sql);
				} catch (Exception e) {
				logger.error("查询异常：{}",e.fillInStackTrace());
				return null;
			} finally {
				if (queryHelper != null) queryHelper.Close();
			}
		}
	}


