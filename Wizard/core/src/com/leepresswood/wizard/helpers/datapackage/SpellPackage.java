package com.leepresswood.wizard.helpers.datapackage;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.leepresswood.wizard.helpers.enums.MagicType;
import com.leepresswood.wizard.helpers.enums.SpellCategory;

/**
 * The LevelHandler will create a data package about the spells. 
 * The EntityHandler will use this information to spawn new spells.
 * @author Lee
 */
public class SpellPackage
{
	//Gathered from constructor.
	public MagicType spell_type;
	public SpellCategory category;
	public int level;
	public int sublevel;
	
	//Data node gathered from it.
	public Element data;
	
	public SpellPackage(MagicType type, SpellCategory category, int level, int sublevel)
	{
		this.spell_type = type;
		this.category = category;
		this.level = level;
		this.sublevel = sublevel;
		
		//Get the correct data from the file.
		try
		{
		   data = new XmlReader().parse(Gdx.files.internal("data/wizards/" + type.toString().toLowerCase() + ".xml")).getChildByName("levels").getChildByName(category.toString().toLowerCase());
		}
		catch(IOException e)
		{
			data = null;
			e.printStackTrace();
		}
	}
	
	/**
	 * Get the root of the spell's main path.
	 * @param pack
	 * @return
	 */
	public static Element getBasic(SpellPackage data_package)
	{
		return data_package.data;
	}
	
	/**
	 * Get the root of the spell's main path.
	 * @param pack
	 * @return
	 */
	public static Element getMain(SpellPackage data_package)
	{
		return data_package.data.getChildByName("main");
	}
	
	/**
	 * Get the root of the spell's main path.
	 * @param pack
	 * @return
	 */
	public static Element getSub(SpellPackage data_package)
	{
		return data_package.data.getChildByName("sub");
	}
	
	/**
	 * Get the root of the spell's main level.
	 * @param pack
	 * @return
	 */
	public static Element getMainLevel(SpellPackage data_package)
	{
		if(data_package.level < 0)
			return null;
		return getMain(data_package).getChild(data_package.level);
	}
	
	/**
	 * Get the root of the spell's sub level.
	 * @param pack
	 * @return
	 */
	public static Element getSubLevel(SpellPackage data_package)
	{
		if(data_package.sublevel < 0)
			return null;
		return getSub(data_package).getChild(data_package.sublevel);
	}
}
