package fr.coppernic.template.di.components

import dagger.Component
import fr.coppernic.template.di.modules.ContextModule
import fr.coppernic.template.home.HomeActivity
import javax.inject.Singleton


@Singleton
@Component(modules = [(ContextModule::class)])
interface AppComponents {

    fun inject(homeActivity: HomeActivity)
}