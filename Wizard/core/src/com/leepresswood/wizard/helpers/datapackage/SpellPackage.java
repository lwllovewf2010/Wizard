package com.leepresswood.wizard.helpers.datapackage;

import com.leepresswood.wizard.helpers.enums.MagicType;
import com.leepresswood.wizard.helpers.enums.SpellCategory;

/**
 * The LevelHandler will create a data package about the spells. 
 * The EntityHandler will use this information to spawn new spells.
 * @author Lee
 */
public class SpellPackage
{
	public MagicType type;
	public SpellCategory category;
	public int level;
	public int sublevel;
	
	public SpellPackage(MagicType type, SpellCategory category, int level, int sublevel)
	{
		this.type = type;
		this.category = category;
		this.level = level;
		this.sublevel = sublevel;
	}
}
