package org.cubeville.effects.managers;

import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

@SerializableAs("EffectManager")
public class EffectManager implements ConfigurationSerializable
{
    List<Effect> effects;
    static EffectManager instance;
    private int runningEffectId = 0;
    List<ParticleEffectTimedRunnable> runningEffects = new ArrayList<>();
    
    public static EffectManager getInstance() {
        return instance;
    }

    public int getNewRunningEffectId() {
        return runningEffectId++;
    }

    public void registerRunningEffect(ParticleEffectTimedRunnable runnable) {
        runningEffects.add(runnable);
    }

    public void deregisterRunningEffect(ParticleEffectTimedRunnable runnable) {
        runningEffects.remove(runnable);
    }

    public List<ParticleEffectTimedRunnable> getRunningEffects() {
        return runningEffects;
    }

    public boolean abortRunningEffect(int id) {
        for(ParticleEffectTimedRunnable r: runningEffects) {
            if(r.getId() == id) {
                r.abort();
                return true;
            }
        }
        return false;
    }

    public boolean isEffectRunning(String name) {
        for(ParticleEffectTimedRunnable r: runningEffects) {
            if(r.getEffect().getName().equals(name))
                return true;
        }
        return false;
    }

    public void abortRunningGroupEffects(String group) {
        if(group == null) return;
        int size = runningEffects.size();
        for(int i = size - 1; i >= 0; i--) {
            if(runningEffects.get(i).getGroup() != null && runningEffects.get(i).getGroup().equals(group)) {
                runningEffects.get(i).abort();
            }
        }
    }

    public EffectManager(Map<String, Object> config) {
        effects = (List<Effect>) config.get("effects");
        instance = this;
    }

    public Map<String, Object> serialize() {
        Map<String, Object> ret = new HashMap<>();
        ret.put("effects", effects);
        return ret;
    }

    public EffectManager() {
        effects = new ArrayList<>();
        instance = this;
    }

    public void addEffect(Effect effect) {
        if(getEffectIdByName(effect.getName()) != -1) throw new IllegalArgumentException("Effect with that name already exists!");
        effects.add(effect);
    }

    public void removeEffect(Effect effect) {
        effects.remove(effect);
    }
    
    public int getEffectIdByName(String name) {
        for(int i = 0; i < effects.size(); i++) {
            if(effects.get(i).getName().equals(name)) return i;
        }
        return -1;
    }

    public List<String> getEffectList() {
        List<String> ret = new ArrayList<>();
        for(Effect effect: effects) {
            ret.add(effect.getName() + ": " + effect.getType());
        }
        return ret;
    }

    public List<Effect> getEffects() {
        return effects;
    }
    
    public Effect getEffectById(int id) {
        return effects.get(id);
    }

    public Effect getEffectByName(String name) {
        int id = getEffectIdByName(name);
        if(id == -1) return null;
        return effects.get(id);
    }

    public List<String> getEffectInfo(String name, boolean detailed, String limit) {
        Effect effect = getEffectByName(name);
        if(effect == null) return null;
        return effect.getInfo(detailed, limit);
    }

    public void updateExternalEffectHookReferences() {
        for(Effect effect: effects) {
            if(effect instanceof EffectWithHook) {
                EffectWithHook e = (EffectWithHook) effect;
                Set<String> names = e.getExternalEffectNames();
                for(String name: names) {
                    Effect externalEffect = getEffectByName(name);
                    e.setExternalEffectReference(name, externalEffect);
                }
            }
        }
    }

    public boolean isEffectInUseAsExternalEffect(String effectName) {
        for(Effect effect: effects) {
            if(effect instanceof EffectWithHook) {
                EffectWithHook e = (EffectWithHook) effect;
                Set<String> effectNames = e.getExternalEffectNames();
                if(effectNames.contains(effectName)) return true;
            }
        }
        return false;
    }
}
