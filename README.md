<h2>Wizard</h2>
Version: <b>0.12 Alpha</b>

A 2D sidescroller made with libGDX. This will eventually be a multiplayer game, but for now, a one-player sidescroller will do.

<h5>Note:</h5>
Logs will be kept for all major patches from Version 0.11 Alpha onward. The most recent patch notes may be seen here. All logs will also be available within the /log directory.

<h4>Version 0.12 Alpha Change Log</h4>
<ul>
	<li>Extra level up points from experience.</li>
	<li>Experience drops on enemy death stored in XML.</li>
	<li>Corrected positioning of decay text.</li>
	<li>Spells now created from packages.</li>
	<li>Changed number of spells to 4.</li>
	<li>Gave each spell an attribute for parallelism between characters.</li>
	<li>Created spells/wizards on a per-file basis rather than all-in-one.</li>
</ul>

<h4>Issues</h4>
<ul>
	<li>No bounds on player movement outside of world.</li>
	<li><del>Enemy movement direction + same knockback direction = Really fast knockback.</del></li>
	<li><del>Dead enemies can damage player.</del></li>
	<li><del>LivingEntity that is invincible interacts with physics of enemy.</del></li>
	<li>XP bar goes in negative direction when gaining XP.</li>
</ul>
