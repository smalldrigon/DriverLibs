package com.grgbanking.huitong.driver_libs.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.grgbanking.huitong.driver_libs.bean.*;
import com.grgbanking.huitong.driver_libs.interfaces.IDatabase;
import org.greenrobot.greendao.AbstractDao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.RecursiveTask;


/**
 * Author: gongxiaobiao
 * Date: on 2020/3/17 16:06
 * Email: 904430803@qq.com
 * Description:
 */
public class DatabaseInstance implements IDatabase {

    public static DatabaseInstance mDatabaseInstance;

    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    private String mDatabaseName = "gateMachine.db";

    private Context context;

    public String getmDatabaseName() {
        return mDatabaseName;
    }

    public void setmDatabaseName(String mDatabaseName) {
        this.mDatabaseName = mDatabaseName;
    }

    public static DatabaseInstance getInstance(Context context, String databaseName) {
        if (mDatabaseInstance == null) {
            synchronized (DatabaseInstance.class) {
                if (mDatabaseInstance == null) {
                    mDatabaseInstance = new DatabaseInstance(context, databaseName);
                }
            }
        }
        return mDatabaseInstance;
    }

    private DatabaseInstance(Context context, String databaseName) {
        this.context = context;
        this.mDatabaseName = databaseName.isEmpty() ? "gateMachine.db" : databaseName;
        mHelper = new DaoMaster.DevOpenHelper(context, mDatabaseName, null);
        mDaoMaster = new DaoMaster(getWhriteableDatabase());
        mDaoSession = mDaoMaster.newSession();


    }

    private SQLiteDatabase getWhriteableDatabase() {

        if (mHelper == null) {
            mHelper = new DaoMaster.DevOpenHelper(context, mDatabaseName, null);
        }
        SQLiteDatabase db = mHelper.getWritableDatabase();
        return db;
    }

    private SQLiteDatabase getReadableDatabase() {
        if (mHelper == null) {
            mHelper = new DaoMaster.DevOpenHelper(context, mDatabaseName, null);
        }
        return mHelper.getReadableDatabase();

    }


    @Override
    public <T> boolean insert(EntyType type, T record) {

        switch (type) {
            case LEFTPASS:
                LeftPassDao dao = mDaoSession.getLeftPassDao();
                insertOrReplace(dao, record);
                break;
            case LEFTUNPASS:
                LeftUnPassDao unpassdao = mDaoSession.getLeftUnPassDao();
                insertOrReplace(unpassdao, record);
                break;

            case RIGHTPASS:
                RightPassDao rightPassDao = mDaoSession.getRightPassDao();
                insertOrReplace(rightPassDao, record);
                break;
            case RIGHTUNPASS:
                RightUnPassDao rightUnPassDao = mDaoSession.getRightUnPassDao();
                insertOrReplace(rightUnPassDao, record);
                break;

        }

        return false;
    }

    private <T> void insertOrReplace(AbstractDao dao, T record) {
        if (record instanceof List) {
            for (Object item : ((List) record)) {
                insert(dao, item);
            }
        } else {
            insert(dao, record);
        }
    }

    private Long insert(AbstractDao dao, Object record) {
        return dao.insertOrReplace(record);
    }

    @Deprecated
    @Override
    public <T> boolean update(EntyType type, T record) {

        switch (type) {
            case LEFTPASS:
                LeftPassDao dao = mDaoSession.getLeftPassDao();
                insertOrReplace(dao, record);
                break;
            case LEFTUNPASS:
                LeftUnPassDao unpassdao = mDaoSession.getLeftUnPassDao();
                insertOrReplace(unpassdao, record);
                break;

            case RIGHTPASS:
                RightPassDao rightPassDao = mDaoSession.getRightPassDao();
                insertOrReplace(rightPassDao, record);
                break;
            case RIGHTUNPASS:
                RightUnPassDao rightUnPassDao = mDaoSession.getRightUnPassDao();
                insertOrReplace(rightUnPassDao, record);
                break;


        }
        return false;
    }

    private <T> void update(AbstractDao dao, T record) {
        if (record instanceof List) {
            for (Object item : ((List) record)) {
                dao.update(item);
            }
        } else {
            dao.update(record);
        }
    }


    @Override
    public <T> boolean delete(EntyType type, T record) {
        switch (type) {
            case LEFTPASS:
                LeftPassDao dao = mDaoSession.getLeftPassDao();
                dao.deleteAll();
                break;
            case LEFTUNPASS:
                LeftUnPassDao unpassdao = mDaoSession.getLeftUnPassDao();
                unpassdao.deleteAll();
                break;

            case RIGHTPASS:
                RightPassDao rightPassDao = mDaoSession.getRightPassDao();
                rightPassDao.deleteAll();
                break;
            case RIGHTUNPASS:
                RightUnPassDao rightUnPassDao = mDaoSession.getRightUnPassDao();
                rightUnPassDao.deleteAll();
                break;


        }
        return false;
    }

    @Override
    public List querry(EntyType type) {

        List list = null;
        switch (type) {
            case LEFTPASS:
                LeftPassDao dao = mDaoSession.getLeftPassDao();
                list = dao.queryBuilder().list();
                break;
            case LEFTUNPASS:
                LeftUnPassDao unpassdao = mDaoSession.getLeftUnPassDao();
                list = (unpassdao.queryBuilder().list());
                break;

            case RIGHTPASS:
                RightPassDao rightPassDao = mDaoSession.getRightPassDao();
                list = (rightPassDao.queryBuilder().list());
                break;
            case RIGHTUNPASS:
                RightUnPassDao rightUnPassDao = mDaoSession.getRightUnPassDao();
                list = (rightUnPassDao.queryBuilder().list());
                break;


        }
        return list;
    }

    @Override
    public long countTotal(EntyType type) {
        long result = 0;
        switch (type) {
            case LEFTPASS:
                LeftPassDao dao = mDaoSession.getLeftPassDao();
                result = dao.queryBuilder().list().size();
                break;
            case LEFTUNPASS:
                LeftUnPassDao unpassdao = mDaoSession.getLeftUnPassDao();
                result = unpassdao.queryBuilder().list().size();
                break;
            case RIGHTPASS:
                RightPassDao rightPassDao = mDaoSession.getRightPassDao();
                result = rightPassDao.queryBuilder().list().size();
                break;
            case RIGHTUNPASS:
                RightUnPassDao rightUnPassDao = mDaoSession.getRightUnPassDao();
                result = rightUnPassDao.queryBuilder().list().size();
                break;
        }
        return result;
    }

    @Override
    public long countToday(EntyType type) {
        long result = 0;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String today = format.format(new Date());

        switch (type) {
            case LEFTPASS:
                LeftPassDao dao = mDaoSession.getLeftPassDao();
                System.out.println(today);
                result = dao.
                        queryBuilder().
                        where(LeftPassDao.Properties.TimeStamp.like("%" + today + "%")).
                        list().
                        size();
                break;
            case LEFTUNPASS:
                LeftUnPassDao leftUnPassDao = mDaoSession.getLeftUnPassDao();
                result = leftUnPassDao.queryBuilder().
                        where(LeftUnPassDao.Properties.TimeStamp.like("%" + today + "%")).list().size();
                break;
            case RIGHTPASS:
                RightPassDao rightPassDao = mDaoSession.getRightPassDao();
                result = rightPassDao.queryBuilder().
                        where(RightPassDao.Properties.TimeStamp.like("%" + today + "%")).list().size();
                break;
            case RIGHTUNPASS:
                RightUnPassDao rightUnPassDao = mDaoSession.getRightUnPassDao();
                result = rightUnPassDao.queryBuilder().
                        where(RightUnPassDao.Properties.TimeStamp.like("%" + today + "%")).list().size();
                break;
        }
        return result;
    }
}
