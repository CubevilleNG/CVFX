package org.cubeville.effects.registry;

// TODO: Somehow remove projectiles from the projectiledamageactions with timeout

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;

import org.cubeville.effects.Effects;
import org.cubeville.effects.hooks.BlockBreakHook;
import org.cubeville.effects.hooks.DamageOtherEntityHook;
import org.cubeville.effects.hooks.Hook;
import org.cubeville.effects.hooks.InteractHook;
import org.cubeville.effects.hooks.MoveHook;
import org.cubeville.effects.hooks.ProjectileHitHook;
import org.cubeville.effects.hooks.ProjectileLaunchHook;
import org.cubeville.effects.managers.Effect;
import org.cubeville.effects.util.ItemUtil;
import org.cubeville.effects.managers.EffectManager;
import org.cubeville.effects.hooklists.HooklistRegistry;

@SerializableAs("Registry")
public class Registry implements ConfigurationSerializable
{
    private Map<Integer, RegistryHook<InteractHook>> interactEvents;
    private Map<Integer, RegistryHook<DamageOtherEntityHook>> damageOtherEntityEvents;
    private Map<Integer, RegistryHook<ProjectileLaunchHook>> projectileLaunchEvents;
    private Map<Integer, RegistryHook<ProjectileHitHook>> projectileHitEvents;
    private Map<Integer, RegistryHook<MoveHook>> moveEvents;
    private Map<Integer, RegistryHook<BlockBreakHook>> blockBreakEvents;
    
    private Map<String, Map<Integer, RegistryHook<Hook>>> eventMaps;
    
    static Registry instance;

    private PermissionList permissionList;

    Map<UUID, Set<ProjectileTrackerAction>> projectileHitActions;
    Map<UUID, ProjectileDamageTracker> projectileDamageActions;

    public Registry() {
	interactEvents = new HashMap<>();
	damageOtherEntityEvents = new HashMap<>();
	projectileLaunchEvents = new HashMap<>();
        projectileHitEvents = new HashMap<>();
        moveEvents = new HashMap<>();
        blockBreakEvents = new HashMap<>();

        initializeEventMaps();

        instance = this;
        permissionList = new PermissionList();

        projectileHitActions = new HashMap<>();
        projectileDamageActions = new HashMap<>();
    }

    public Registry(Map<String, Object> config) {
        // TODO!!! : wha?
        interactEvents = (Map<Integer, RegistryHook<InteractHook>>) config.get("interact");
        damageOtherEntityEvents = (Map<Integer, RegistryHook<DamageOtherEntityHook>>) config.get("damage");
        projectileLaunchEvents = (Map<Integer, RegistryHook<ProjectileLaunchHook>>) config.get("projectilelaunch");
        projectileHitEvents = (Map<Integer, RegistryHook<ProjectileHitHook>>) config.get("projectilehit");
        moveEvents = (Map<Integer, RegistryHook<MoveHook>>) config.get("move");
        if(moveEvents == null) moveEvents = new HashMap<>();
        blockBreakEvents = (Map<Integer, RegistryHook<BlockBreakHook>>) config.get("blockbreak");
        if(blockBreakEvents == null) blockBreakEvents = new HashMap<>();
        initializeEventMaps();
        permissionList = (PermissionList) config.get("permissionList");
        instance = this;
        projectileHitActions = new HashMap<>();
        projectileDamageActions = new HashMap<>();
    }

    private void initializeEventMaps() {
        eventMaps = new HashMap<>();
        eventMaps.put("interact", (Map) interactEvents);
        eventMaps.put("damage", (Map) damageOtherEntityEvents);
        eventMaps.put("projectilelaunch", (Map) projectileLaunchEvents);
        eventMaps.put("projectilehit", (Map) projectileHitEvents);
        eventMaps.put("move", (Map) moveEvents);
        eventMaps.put("blockbreak", (Map) blockBreakEvents);
    }
    
    public static Registry getInstance() {
        return instance;
    }

    public PermissionList getPermissionList() {
        return permissionList;
    }
    
    public Map<String, Object> serialize() {
        Map<String, Object> ret = new HashMap<>();
        for(String key: eventMaps.keySet()) ret.put(key, eventMaps.get(key));
        ret.put("permissionList", permissionList);
        return ret;
    }

    public List<String> getHookList() {
        List<String> ret = new ArrayList<>();
        for(String key: eventMaps.keySet()) {
            ret.add(key + ":");
            for(Integer hk: eventMaps.get(key).keySet()) {
                ret.add("  " + hk + ":");
                int cnt = 1;
                for(Hook h: eventMaps.get(key).get(hk).getHooks()) {
                    ret.add("    " + (cnt++) + ") " + ((Hook) h).getInfo());
                }
            }
        }
        return ret;
    }

    public List<String> getHookList(Integer id) {
        List<String> ret = new ArrayList<>();
        for(String key: eventMaps.keySet()) {
            if(eventMaps.get(key).containsKey(id)) {
                RegistryHook<Hook> rh = eventMaps.get(key).get(id);
                String i = "";
                if(rh.getPermission() != null) i += rh.getPermission();
                if(rh.getCooldown() != 0) {
                    if(i.length() > 0) i += ",";
                    i += (Double.valueOf(rh.getCooldown()) / 1000);
                }
                if(i.length() > 0) i = " (" + i + ")";
                ret.add(key + i + ":");
                int cnt = 1;
                for(Hook h: rh.getHooks()) {
                    ret.add("  " + (cnt++) + ") " + ((Hook) h).getInfo());
                }
            }
        }
        return ret;
    }

    private Map<Integer, RegistryHook<Hook>> getMapByHookType(Hook hook) {
        Map<Integer, RegistryHook<Hook>> ret = null;
        if(hook instanceof InteractHook) {
            ret = (Map)interactEvents;
        }
        else if(hook instanceof DamageOtherEntityHook) {
            ret = (Map)damageOtherEntityEvents;
        }
        else if(hook instanceof ProjectileLaunchHook) {
            ret = (Map)projectileLaunchEvents;
        }
        else if(hook instanceof MoveHook) {
            ret = (Map)moveEvents;
        }
        else if(hook instanceof BlockBreakHook) {
            ret = (Map)blockBreakEvents;
        }
        else if(hook instanceof ProjectileHitHook) {
            ret = (Map)projectileHitEvents;
        }
        return ret;
    }
    
    public void registerEvent(Integer id, Hook hook) {
        Map<Integer, RegistryHook<Hook>> map = getMapByHookType(hook);
        if(map.get(id) == null) map.put(id, new RegistryHook<>());
        map.get(id).getHooks().add(hook);
    }
    
    public void processInteractEvent(PlayerInteractEvent event) {
	    if(event.getHand() != EquipmentSlot.HAND) return;
        if (!ItemUtil.hasHooklist(event.getItem())) return;
        
        List<Integer> ids = ItemUtil.getHooklistIDs(event.getItem());
        if (ids == null) return;
        for (Integer id : ids) {
            if (!interactEvents.containsKey(id)) continue;
            
            RegistryHook<InteractHook> rh = interactEvents.get(id);
            boolean isPermitted = rh.isPermitted(event.getPlayer().getUniqueId());
            boolean processing = true;
            for (InteractHook hook : rh.getHooks()) {
                if ((processing && isPermitted) || hook.alwaysActive()) {
                    if (hook.process(event) == false) {
                        processing = false;
                    }
                }
            }
        }
    }

    public List<InteractHook> getInteractHooksOfItem(Integer id) {
        if(!interactEvents.containsKey(id)) {
            return null;
        }
        List<InteractHook> hooks = interactEvents.get(id).getHooks();
        return hooks;
    }

    public List<ProjectileLaunchHook> getProjectileLaunchHooksOfItem(Integer id) {
        if(!projectileLaunchEvents.containsKey(id))
            return null;
        List<ProjectileLaunchHook> hooks = projectileLaunchEvents.get(id).getHooks();
        return hooks;
    }

    public void processMoveEvent(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        List<Integer> ids = new ArrayList<>();
        for (ItemStack item : player.getInventory().getArmorContents()) {
            if (!ItemUtil.hasHooklist(item)) continue;
            List<Integer> newIDS = ItemUtil.getHooklistIDs(item);
            if (newIDS != null) {
                ids.addAll(ItemUtil.getHooklistIDs(item));
            }
        }
        
        for (Integer id : ids) {
            
            if (!moveEvents.containsKey(id)) continue;
            
            RegistryHook<MoveHook> rh = moveEvents.get(id);
            boolean isPermitted = rh.isPermitted(event.getPlayer().getUniqueId());
            for (MoveHook hook : rh.getHooks()) {
                if (isPermitted || hook.alwaysActive()) {
                    hook.process(event);
                }
            }
        }
    }

    public void processBlockBreakEvent(BlockBreakEvent event) {
        Player player = event.getPlayer();

        ItemStack heldItem = event.getPlayer().getInventory().getItemInMainHand();
        if(heldItem == null) return;
        List<Integer> ids = ItemUtil.getHooklistIDs(heldItem);
        if (ids == null) return;

        for (Integer id : ids) {
            if (blockBreakEvents.containsKey(id)) {
                RegistryHook<BlockBreakHook> rh = blockBreakEvents.get(id);
                boolean isPermitted = rh.isPermitted(player.getUniqueId());
                for (BlockBreakHook hook : rh.getHooks()) {
                    if (isPermitted || hook.alwaysActive()) {
                        hook.process(event);
                    }
                }
            }
        }
    }

    public void deregisterInteractEvent(Integer id, int index) {
        deregisterEvent(interactEvents, id, index);
    }

    public void deregisterProjectileLaunchEvent(Integer id, int index) {
        deregisterEvent(projectileLaunchEvents, id, index);
    }

    public void deregisterProjectileHitEvent(Integer id, int index) {
        deregisterEvent(projectileHitEvents, id, index);
    }

    public void deregisterMoveEvent(Integer id, int index) {
        deregisterEvent(moveEvents, id, index);
    }

    public void deregisterBlockBreakEvent(Integer id, int index) {
        deregisterEvent(blockBreakEvents, id, index);
    }
    
    public void processEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        if(event.getDamager() instanceof Player) {
            Player damager = (Player) event.getDamager();
            List<Integer> ids = ItemUtil.getHooklistIDs(damager.getInventory().getItemInMainHand());
            if (ids == null) return;
            for (Integer id : ids) {
                if (damageOtherEntityEvents.containsKey(id)) {
                    RegistryHook<DamageOtherEntityHook> rh = damageOtherEntityEvents.get(id);
                    boolean isPermitted = rh.isPermitted(damager.getUniqueId());
                    for (DamageOtherEntityHook hook : rh.getHooks()) {
                        if (isPermitted || hook.alwaysActive()) {
                            hook.process(event);
                        }
                    }
                }
            }
        }
        else if(event.getDamager() instanceof Projectile) {
            Projectile damager = (Projectile) event.getDamager();
            if(damager.getShooter() instanceof Player) {
                UUID uuid = damager.getUniqueId();
                if(projectileDamageActions.containsKey(uuid)) {
                    RegistryHook<DamageOtherEntityHook> rh = projectileDamageActions.get(uuid).getRegistryHook();
                    boolean isPermitted = rh.isPermitted(((Player) damager.getShooter()).getUniqueId());
                    for(DamageOtherEntityHook hook: rh.getHooks()) {
                        if(isPermitted || hook.alwaysActive()) {
                            hook.process(event);
                        }
                    }
                }
            }
        }
    }

    public void deregisterDamageOtherEntityEvent(Integer id, int index) {
        deregisterEvent(damageOtherEntityEvents, id, index);
    }

    public void processProjectileLaunchEvent(ProjectileLaunchEvent event) {
        if(!(event.getEntity() instanceof Projectile)) return;
        ProjectileSource shooter = ((Projectile) event.getEntity()).getShooter();
        if(!(shooter instanceof Player)) return;
        Player player = (Player) shooter;

        // TODO: Will the last snowball also trigger?
        List<Integer> ids = ItemUtil.getHooklistIDs(player.getInventory().getItemInMainHand());
        if (ids == null) return;
        
        for (Integer id : ids) {
            if (projectileLaunchEvents.containsKey(id)) {
                RegistryHook<ProjectileLaunchHook> rh = projectileLaunchEvents.get(id);
                boolean isPermitted = rh.isPermitted(player.getUniqueId());
                for (ProjectileLaunchHook hook : rh.getHooks()) {
                    if (isPermitted || hook.alwaysActive()) {
                        hook.process(event);
                    }
                }
            }
        }

        // TODO!
        // if(projectileHitEvents.containsKey(itemName)) {
        //     addProjectileHitAction((Projectile) event.getEntity(), new ProjectileTrackerHookProcessor(projectileHitEvents.get(itemName)));
        // }
        for (Integer id : ids) {
            if (damageOtherEntityEvents.containsKey(id)) {
                addProjectileDamageAction((Projectile) event.getEntity(), damageOtherEntityEvents.get(id));
            }
        }
    }

    private void deregisterEvent(Map<Integer, ?> map, Integer id, int index) {
        RegistryHook rh = (RegistryHook) map.get(id);
        List<?> list = rh.getHooks();
        if(list == null) throw new IllegalArgumentException("No hooks available for hooklist " + id + ".");
        if(index < 1 || index > list.size()) throw new IllegalArgumentException("No hook nr " + index + " available.");
        list.remove(index - 1);
        if(list.size() == 0) map.remove(id);
    }

    public boolean isEffectInUse(Effect effect) {
        for(Map<Integer, RegistryHook<Hook>> eventMap: eventMaps.values()) {
            for(Integer id: eventMap.keySet()) {
                if(hookListUsesEffect(effect, eventMap.get(id).getHooks())) return true;
            }
        }
        return false;
    }

    private boolean hookListUsesEffect(Effect effect, List<?> list) {
        List<Hook> hooklist = (List<Hook>) list;
        for(Hook h: hooklist) {
            if(h.usesEffect(effect)) return true;
        }
        return false;
    }

    public void addProjectileHitAction(Projectile projectile, ProjectileTrackerAction action) {
        UUID uuid = projectile.getUniqueId();
        if(!projectileHitActions.containsKey(uuid)) {
            projectileHitActions.put(uuid, new HashSet<ProjectileTrackerAction>());
        }
        projectileHitActions.get(uuid).add(action);
    }

    public void processProjectileHitEvent(ProjectileHitEvent event) {
        /*Projectile projectile = event.getEntity();
        UUID uuid = projectile.getUniqueId();
        if(projectileHitActions.containsKey(uuid)) {
            for(ProjectileTrackerAction action: projectileHitActions.get(uuid)) {
                action.projectileHitEvent();
            }
        }
        if(projectileDamageActions.containsKey(uuid)) {
            Bukkit.getScheduler().runTask(Effects.getInstance(), () -> projectileDamageActions.remove(uuid));
        }*/
        if(!(event.getEntity().getShooter() instanceof Player)) return;
        Player player = (Player) event.getEntity().getShooter();
        List<Integer> ids = ItemUtil.getHooklistIDs(player.getInventory().getItemInMainHand());
        if (ids == null) return;
        
        for (Integer id : ids) {
            if (projectileHitEvents.containsKey(id)) {
                RegistryHook<ProjectileHitHook> rh = projectileHitEvents.get(id);
                boolean isPermitted = rh.isPermitted(player.getUniqueId());
                for (ProjectileHitHook hook : rh.getHooks()) {
                    if (isPermitted || hook.alwaysActive()) {
                        hook.process(event);
                    }
                }
            }
        }
    }

    public void removeProjectileHitAction(Projectile projectile, ProjectileTrackerAction action) {
        UUID uuid = projectile.getUniqueId();
        Set<ProjectileTrackerAction> set = projectileHitActions.get(uuid);
        if(set != null) {
            set.remove(action);
            if(set.size() == 0) projectileHitActions.remove(uuid);
        }
    }
    
    public void addProjectileDamageAction(Projectile projectile, RegistryHook<DamageOtherEntityHook> registryHook) {
        UUID uuid = projectile.getUniqueId();
        projectileDamageActions.put(uuid, new ProjectileDamageTracker(registryHook));
    }

    public void setPermission(Integer id, String eventClass, String permission) {
        Map<Integer, RegistryHook<Hook>> eventMap = (Map<Integer, RegistryHook<Hook>>) eventMaps.get(eventClass);
        if(!eventMap.containsKey(id)) throw new IllegalArgumentException("Item not defined in this event class.");
        eventMap.get(id).setPermission(permission);
    }

    public void setCooldown(Integer id, String eventClass, double cooldown) {
        Map<Integer, RegistryHook<Hook>> eventMap = (Map<Integer, RegistryHook<Hook>>) eventMaps.get(eventClass);
        if(!eventMap.containsKey(id)) throw new IllegalArgumentException("Item not defined in this event class.");
        eventMap.get(id).setCooldown(new Double(cooldown * 1000).intValue());
    }
    
    public Set<String> getEventClasses() {
        return eventMaps.keySet();
    }

    public void clearPermissionCache() {
        for(Map<Integer, RegistryHook<Hook>> eventMap: eventMaps.values()) {
            for(RegistryHook<Hook> hook: eventMap.values()) {
                hook.clearPermissionCache();
            }
        }
    }
    
    public void cleanHooklists() {

        showHooklistInfo();
        
        for(String key: eventMaps.keySet()) {
            Map<Integer, RegistryHook<Hook>> hooklists = eventMaps.get(key);
            Set<Integer> hooklistIds = hooklists.keySet();
            Set<Integer> deleteIds = new HashSet<>();
            for(int id: hooklistIds) {
                RegistryHook<Hook> rh = hooklists.get(id);
                List<Hook> hooks = rh.getHooks();
                boolean isused = false;

                for(Hook h: hooks) {
                    for(Effect e: EffectManager.getInstance().getEffects()) {
                        if(h.usesEffect(e)) {
                            isused = true;
                        }
                    }

                }

                if(isused == false) {
                    deleteIds.add(id);
                    System.out.println("Delete hook " + id);
                }
            }

            for(int id: deleteIds) {
                hooklists.remove(id);
                HooklistRegistry.getInstance().removeHooklist(id);
            }
        }

        showHooklistInfo();

    }

    public void showHooklistInfo() {
        System.out.println("Hooklist list:");
        for(String key: eventMaps.keySet()) {
            Map<Integer, RegistryHook<Hook>> hooklists = eventMaps.get(key);
            Set<Integer> hooklistIds = hooklists.keySet();
            for(int id: hooklistIds) {
                System.out.println("Found hooklist id " + id);
            }
        }
    }
    
}
