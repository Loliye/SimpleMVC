package org.mikufans;

import org.mikufans.core.ConfigHelper;

public interface SimpleConstants
{
    String UTF_8 = "UTF-8";

    String CONFIG_PROPS = "simple.properties";
    String SQL_PROPS = "simple-sql.properties";

    String PLUGIN_PAGEAGE = "simple.plugin";

    String PK_NAME = "id";
    String PACKAGE_PATH = "simple.app_base_package";
    String DataBaseType = "simple.jdbc.type";
    String DRIVER = "simple.jdbc.driver";
    String URL = "simple.jdbc.url";
    String username = "simple.jdbc.username";
    String password = "simple.jdbc.password";
    String PLUGIN_PACKAGE = "simple.plugin";


    String STATIC_PATH = ConfigHelper.getString("simple.static_path", "/static/");
    String JSP_PATH = ConfigHelper.getString("simple.jsp_path", "/");
    String HOME_PAGE = ConfigHelper.getString("simple.home_page", "/index.jsp");
    int UPLOAD_LIMIT = ConfigHelper.getInt("simple.upload_limit", 10);

    String UPLOAD_PATH= ConfigHelper.getString("simple.upload_path","/WEB-INF/uploadFiles");

    String DRUID_URL=ConfigHelper.getString("druid.url","/druid/**");
}
