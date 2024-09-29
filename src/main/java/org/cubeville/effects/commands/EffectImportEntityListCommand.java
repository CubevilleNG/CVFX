package org.cubeville.effects.commands;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.util.Transformation;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import org.joml.AxisAngle4f;

import org.cubeville.commons.commands.CommandExecutionException;
import org.cubeville.commons.commands.Command;
import org.cubeville.commons.commands.CommandParameterString;
import org.cubeville.commons.commands.CommandResponse;
import org.cubeville.effects.managers.ParticleEffect;
import org.cubeville.effects.managers.ParticleEffectComponent;
import org.cubeville.effects.managers.EffectManager;
import org.cubeville.effects.managers.sources.value.ConstantValueSource;
import org.cubeville.effects.managers.DisplayEntityProperties;

import org.cubeville.cventityedit.Selection;

public class EffectImportEntityListCommand extends Command
{
    public EffectImportEntityListCommand() {
        super("effect importentitylist");
        addBaseParameter(new CommandParameterString());
    }

    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters)
        throws CommandExecutionException {

        String name = (String) baseParameters.get(0);
        ParticleEffect effect = new ParticleEffect(name);
        
        List<Entity> entities = Selection.getInstance().getSelectedEntities(player);
        if(entities.size() == 0) throw new CommandExecutionException("No selection!");

        int cnt = 0;
        for(Entity e: entities) {
            if(e.getType() != EntityType.ITEM_DISPLAY) continue;
            ItemDisplay id = (ItemDisplay) e;
            
            cnt++;
            ParticleEffectComponent component = new ParticleEffectComponent();
            effect.addComponent(component);

            component.setParticle(null);
            DisplayEntityProperties dep = component.createOrGetDisplayEntityProperties();
            dep.setItemData(id.getItemStack());

            Transformation t = id.getTransformation();
            dep.moveX = new ConstantValueSource(t.getTranslation().x);
            dep.moveY = new ConstantValueSource(t.getTranslation().y);
            dep.moveZ = new ConstantValueSource(t.getTranslation().z);
            dep.scaleX = new ConstantValueSource(t.getScale().x);
            dep.scaleY = new ConstantValueSource(t.getScale().y);
            dep.scaleZ = new ConstantValueSource(t.getScale().z);
            AxisAngle4f lr = new AxisAngle4f(t.getLeftRotation());
            dep.rotateLeftAngle = new ConstantValueSource(Math.toDegrees(lr.angle));
            dep.rotateLeftX = new ConstantValueSource(lr.x);
            dep.rotateLeftY = new ConstantValueSource(lr.y);
            dep.rotateLeftZ = new ConstantValueSource(lr.z);
            AxisAngle4f rr = new AxisAngle4f(t.getRightRotation());
            dep.rotateRightAngle = new ConstantValueSource(Math.toDegrees(rr.angle));
            dep.rotateRightX = new ConstantValueSource(rr.x);
            dep.rotateRightY = new ConstantValueSource(rr.y);
            dep.rotateRightZ = new ConstantValueSource(rr.z);
        }

        if(cnt == 0) throw new CommandExecutionException("No item displays in selection!");

        EffectManager.getInstance().addEffect(effect);
        CommandUtil.saveConfig();

        return null;
    }

}
