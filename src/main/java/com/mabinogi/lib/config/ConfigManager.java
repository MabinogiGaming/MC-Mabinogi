package com.mabinogi.lib.config;

import static com.mabinogi.lib.Mabinogi.LOG;

import java.io.File;

import com.mabinogi.lib.plugin.IPlugin;
import com.mabinogi.lib.plugin.PluginManager;

import net.minecraftforge.common.config.Configuration;

public abstract class ConfigManager {
	
	public Configuration config;
	
	public static final String CATEGORY_MODULES = "Modules";
	
	public ConfigManager(File directory)
	{
		config = new Configuration(new File(directory.getPath(), "scholastic.cfg"));
		
		load();
	}
	
	public void load() 
	{
		if (config == null)
		{
			LOG.error("Unable to read configuration, null config");
			return;
		}
		
		try 
        {
			config.load();
			
			init();
        }
        catch (Exception exception) 
        {
        	LOG.error("Unable to read configuration, exception", exception);
        } 
        finally 
        {
        	save();
        }
    }
	
	public void save()
	{
		if (config.hasChanged()) 
        {
        	config.save();
        }
	}
	
	public abstract void init();
	
	public void initPlugins(PluginManager manager)
	{
		config.addCustomCategoryComment(CATEGORY_MODULES, "Enable or disable modules");
		
		for (IPlugin plugin : manager.plugins)
		{
			plugin.configPlugin(config);
		}
	}

}
