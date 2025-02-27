package org.cubeville.effects;

import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.cubeville.effects.hooklists.Hooklist;
import org.cubeville.effects.hooklists.HooklistRegistry;

public class SerializationRegistration
{
    public static void init() {
        ConfigurationSerialization.registerClass(org.cubeville.effects.hooks.BlockBreakHookBlockLocation.class);
        ConfigurationSerialization.registerClass(org.cubeville.effects.hooks.BlockBreakHookParticlePlayer.class);
        ConfigurationSerialization.registerClass(org.cubeville.effects.hooks.DamageOtherEntityHookCancelEvent.class);
        ConfigurationSerialization.registerClass(org.cubeville.effects.hooks.DamageOtherEntityHookEventAndDamager.class);
        ConfigurationSerialization.registerClass(org.cubeville.effects.hooks.DamageOtherEntityHookTargetEntity.class);
        ConfigurationSerialization.registerClass(org.cubeville.effects.hooks.InteractHookCancelEvent.class);
        ConfigurationSerialization.registerClass(org.cubeville.effects.hooks.InteractHookParticlePlayer.class);
        ConfigurationSerialization.registerClass(org.cubeville.effects.hooks.InteractHookPlayer.class);
        ConfigurationSerialization.registerClass(org.cubeville.effects.hooks.InteractHookPlayerLocation.class);
        ConfigurationSerialization.registerClass(org.cubeville.effects.hooks.InteractHookRemoveItem.class);
        ConfigurationSerialization.registerClass(org.cubeville.effects.hooks.InteractHookTargetEntity.class);
        ConfigurationSerialization.registerClass(org.cubeville.effects.hooks.InteractHookTargetLocation.class);
        ConfigurationSerialization.registerClass(org.cubeville.effects.hooks.InteractHookTargetLocationParticlePlayer.class);
        ConfigurationSerialization.registerClass(org.cubeville.effects.hooks.MoveHookPlayerLocation.class);
        ConfigurationSerialization.registerClass(org.cubeville.effects.hooks.ProjectileHitHookHitLocation.class);
        ConfigurationSerialization.registerClass(org.cubeville.effects.hooks.ProjectileLaunchHookCancelEvent.class);
        ConfigurationSerialization.registerClass(org.cubeville.effects.hooks.ProjectileLaunchHookParticlePlayer.class);
        ConfigurationSerialization.registerClass(org.cubeville.effects.hooks.ProjectileLaunchHookPlayerLocation.class);
        ConfigurationSerialization.registerClass(org.cubeville.effects.managers.ArmorStandProperties.class);
        ConfigurationSerialization.registerClass(org.cubeville.effects.managers.CommandWithLivingEntityEffect.class);
        ConfigurationSerialization.registerClass(org.cubeville.effects.managers.CommandWithLocationEffect.class);
        ConfigurationSerialization.registerClass(org.cubeville.effects.managers.DisplayEntityProperties.class);
        ConfigurationSerialization.registerClass(org.cubeville.effects.managers.InteractHookPlayerEffect.class);
        ConfigurationSerialization.registerClass(org.cubeville.effects.managers.NPCEffect.class);
        ConfigurationSerialization.registerClass(org.cubeville.effects.managers.ParticleEffect.class);
        ConfigurationSerialization.registerClass(org.cubeville.effects.managers.ParticleEffectComponent.class);
        ConfigurationSerialization.registerClass(org.cubeville.effects.managers.ParticleEffectTimelineEntry.class);
        ConfigurationSerialization.registerClass(org.cubeville.effects.managers.PiercingEffect.class);
        ConfigurationSerialization.registerClass(org.cubeville.effects.managers.PotionEffectEffect.class);
        ConfigurationSerialization.registerClass(org.cubeville.effects.managers.SoundEffect.class);
        ConfigurationSerialization.registerClass(org.cubeville.effects.managers.SpawnFrogWithLivingEntityEffect.class);
        ConfigurationSerialization.registerClass(org.cubeville.effects.managers.modifier.CoordinateModifierAdvRotate.class);
        ConfigurationSerialization.registerClass(org.cubeville.effects.managers.modifier.CoordinateModifierMove.class);
        ConfigurationSerialization.registerClass(org.cubeville.effects.managers.modifier.CoordinateModifierRotate.class);
        ConfigurationSerialization.registerClass(org.cubeville.effects.managers.modifier.CoordinateModifierScale.class);
        ConfigurationSerialization.registerClass(org.cubeville.effects.managers.modifier.CoordinateModifierScale2d.class);
        ConfigurationSerialization.registerClass(org.cubeville.effects.managers.sources.coordinate.CircleCoordinateSource.class);
        ConfigurationSerialization.registerClass(org.cubeville.effects.managers.sources.coordinate.ConstantCoordinateSource.class);
        ConfigurationSerialization.registerClass(org.cubeville.effects.managers.sources.coordinate.RandomCoordinateSource.class);
        ConfigurationSerialization.registerClass(org.cubeville.effects.managers.sources.value.ConstantValueSource.class);
        ConfigurationSerialization.registerClass(org.cubeville.effects.managers.sources.value.LinearValueSource.class);
        ConfigurationSerialization.registerClass(org.cubeville.effects.managers.sources.value.ListValueSource.class);
        ConfigurationSerialization.registerClass(org.cubeville.effects.managers.sources.value.MultiValueSource.class);
        ConfigurationSerialization.registerClass(org.cubeville.effects.managers.sources.value.RandomValueSource.class);
        ConfigurationSerialization.registerClass(org.cubeville.effects.managers.sources.value.SinewaveValueSource.class);
        ConfigurationSerialization.registerClass(org.cubeville.effects.registry.RegistryHook.class);
        ConfigurationSerialization.registerClass(Hooklist.class);
        ConfigurationSerialization.registerClass(HooklistRegistry.class);
    }
}
