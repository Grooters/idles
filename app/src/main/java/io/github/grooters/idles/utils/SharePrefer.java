package io.github.grooters.idles.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

public class SharePrefer {

    public static <V>void set(Context context, String fileName, String key, V message){

        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);

        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();

        if( message instanceof  String){

            editor.putString(key, (String) message);

        }else if(message instanceof Intenter){

            editor.putInt(key, (Integer) message);

        }else if( message instanceof Boolean){

            editor.putBoolean(key, (Boolean) message);

        }else if(message instanceof Float){

            editor.putFloat(key, (Float)message);

        }else if( message instanceof Long){

            editor.putLong(key, (Long) message);

        }

        editor.apply();

    }

    public static <V>void set(Context context, String fileName, String key, V[] messages){

        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);

        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();

        for(V message : messages){

            if( message instanceof  String){

                editor.putString(key, (String) message);

            }else if(message instanceof Intenter){

                editor.putInt(key, (Integer) message);

            }else if( message instanceof Boolean){

                editor.putBoolean(key, (Boolean) message);

            }else if(message instanceof Float){

                editor.putFloat(key, (Float)message);

            }else if( message instanceof Long){

                editor.putLong(key, (Long) message);

            }

        }

        editor.apply();

    }

    public static <K, V>void set(Context context, String fileName, Map<K,V> map){

        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);

        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();

        for(K key : map.keySet()){

            if( map.get(key) instanceof  String){

                editor.putString((String) key, (String) map.get(key));

            }else if( map.get(key) instanceof Intenter){

                editor.putInt((String) key, (Integer) map.get(key));

            }else if( map.get(key) instanceof Boolean){

                editor.putBoolean((String) key, (Boolean) map.get(key));

            }else if( map.get(key) instanceof Float){

                editor.putFloat((String) key, (Float) map.get(key));

            }else if( map.get(key) instanceof Long){

                editor.putLong((String) key, (Long) map.get(key));

            }

        }

        editor.apply();

    }

    public static void remove(Context context, String fileName, String key){

        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);

        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.remove(key);

    }

    public static <V>Object get(Context context, String fileName, String key, Class<V> cls){

        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);

        if( cls ==  String.class){

            return sharedPreferences.getString(key, "");

        }else if(cls == Intenter.class){

            return sharedPreferences.getInt(key, -1);

        }else if(cls == Float.class){

            return sharedPreferences.getFloat(key, -1);

        }else if(cls == Boolean.class){

            return sharedPreferences.getBoolean(key, false);

        }else if(cls == Long.class){

            return sharedPreferences.getLong(key, -1);

        }

        return null;

    }

}
