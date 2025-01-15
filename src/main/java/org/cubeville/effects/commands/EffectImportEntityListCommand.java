package org.cubeville.effects.commands;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.util.Vector;
import org.bukkit.Location;
import org.bukkit.entity.TextDisplay;
import org.bukkit.util.Transformation;
import org.bukkit.entity.ItemDisplay;
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
import org.cubeville.effects.managers.sources.value.ConstantValueSource;
import org.cubeville.effects.managers.modifier.CoordinateModifierMove;
import org.cubeville.effects.managers.modifier.CoordinateModifierAdvRotate;

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
        Location refloc = null;

        for(Entity e: entities) {
            if(e instanceof ItemDisplay) {
                ItemDisplay id = (ItemDisplay) e;

                ParticleEffectComponent component = new ParticleEffectComponent();
                effect.addComponent(component);

                if(refloc == null)
                    refloc = e.getLocation();
                else {
                    Vector relloc = e.getLocation().toVector().subtract(refloc.toVector());
                    if(relloc.getX() != 0.0)
                        component.addModifier(new CoordinateModifierMove(new ConstantValueSource(relloc.getX()), true, false, false));
                    if(relloc.getY() != 0.0)
                        component.addModifier(new CoordinateModifierMove(new ConstantValueSource(relloc.getY()), false, true, false));
                    if(relloc.getZ() != 0.0)
                        component.addModifier(new CoordinateModifierMove(new ConstantValueSource(relloc.getZ()), false, false, true));
                }

                // TODO: This doesn't work properly with display entities for yet unknown reasons
                // if(e.getLocation().getYaw() != 0.0)
                //     component.addModifier(new CoordinateModifierAdvRotate(new ConstantValueSource(e.getLocation().getYaw()), "xz"));
                // if(e.getLocation().getPitch() != 0.0)
                //     component.addModifier(new CoordinateModifierAdvRotate(new ConstantValueSource(e.getLocation().getPitch()), "xy"));

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

                cnt++;
            }
            else if(e instanceof TextDisplay) {
                TextDisplay td = (TextDisplay) e;

                ParticleEffectComponent component = new ParticleEffectComponent();
                effect.addComponent(component);

                component.setParticle(null);
                DisplayEntityProperties dep = component.createOrGetDisplayEntityProperties();
                dep.setText(td.getText());

                Transformation t = td.getTransformation();
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

                cnt++;
            }
        }

        if(cnt == 0) throw new CommandExecutionException("No item or text displays in selection!");

        EffectManager.getInstance().addEffect(effect);
        CommandUtil.saveConfig();

        return null;
    }

}
