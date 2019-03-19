/* *********************************************************************** *
 * project: org.matsim.*												   *
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2008 by the members listed in the COPYING,        *
 *                   LICENSE and WARRANTY file.                            *
 * email           : info at matsim dot org                                *
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *   See also COPYING, LICENSE and WARRANTY file                           *
 *                                                                         *
 * *********************************************************************** */
package org.matsim.run;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.population.*;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.Controler;
import org.matsim.core.controler.OutputDirectoryHierarchy.OverwriteFileSetting;
import org.matsim.core.scenario.ScenarioUtils;

/**
 * @author nagel
 *
 */
public class RunMatsim {

	public static void main(String[] args) {
		
		Config config ;
		if ( args.length==0 || args[0]=="" ) {
			config = ConfigUtils.loadConfig( "scenarios/MATSim-ITMOcourse/Config.xml" ) ;
			config.controler().setLastIteration(10);
			config.controler().setOverwriteFileSetting( OverwriteFileSetting.deleteDirectoryIfExists );
		} else {
			config = ConfigUtils.loadConfig(args[0]) ;
		}

		Scenario scenario = ScenarioUtils.loadScenario(config);

        NetworkFactory networkFactory = scenario.getNetwork().getFactory();
        Node node0 = networkFactory.createNode(Id.createNodeId("0"), new Coord(0,0));
        Node node1 = networkFactory.createNode(Id.createNodeId("1"), new Coord(0, 1000) );
        Node node2 = networkFactory.createNode(Id.createNodeId("2"), new Coord(0, 2000) );
        Node node3 = networkFactory.createNode(Id.createNodeId("3"), new Coord(1000, 3000) );
        Node node4 = networkFactory.createNode(Id.createNodeId("4"), new Coord(-1000, 3000) );
        Node node5 = networkFactory.createNode(Id.createNodeId("5"), new Coord(0, 4000) );
        Node node6 = networkFactory.createNode(Id.createNodeId("6"), new Coord(0, 5000) );

        Link link1 = networkFactory.createLink((Id.createLinkId(0)), node0, node1);
        link1.setFreespeed(25);
        link1.setLength(100);
        link1.setCapacity(3600);
        Link link2 = networkFactory.createLink((Id.createLinkId(1)), node1, node2);
        link2.setLength(100);
        link2.setFreespeed(25);
        link2.setCapacity(3600);
        Link link3 = networkFactory.createLink((Id.createLinkId(2)), node2, node3);
        link3.setLength(1332);
        link3.setFreespeed(22.2);
        link3.setCapacity(1800);
        Link link4 = networkFactory.createLink((Id.createLinkId(3)), node2, node4);
        link4.setFreespeed(22.2);
        link4.setLength(13320);
        link4.setCapacity(1800);
        Link link5 = networkFactory.createLink((Id.createLinkId(4)), node3, node4);
        link5.setCapacity(1800);
        link5.setFreespeed(22.2);
        link5.setLength(1332);
        Link link6 = networkFactory.createLink((Id.createLinkId(5)), node3, node5);
        link5.setFreespeed(22.2);
        link5.setLength(13320);
        link5.setCapacity(1800);
        Link link7 = networkFactory.createLink((Id.createLinkId(6)), node4, node5);
        link5.setCapacity(1800);
        link5.setFreespeed(22.2);
        link5.setLength(1332);
        Link link8 = networkFactory.createLink((Id.createLinkId(7)), node5, node6);
        link1.setFreespeed(25);
        link1.setLength(100);
        link1.setCapacity(3600);
        scenario.getNetwork().addNode(node0);
        scenario.getNetwork().addNode(node1);
        scenario.getNetwork().addNode(node2);
        scenario.getNetwork().addNode(node3);
        scenario.getNetwork().addNode(node4);
        scenario.getNetwork().addNode(node5);
        scenario.getNetwork().addNode(node6);
        scenario.getNetwork().addLink(link1);
        scenario.getNetwork().addLink(link2);
        scenario.getNetwork().addLink(link3);
        scenario.getNetwork().addLink(link4);
        scenario.getNetwork().addLink(link5);
        scenario.getNetwork().addLink(link6);
        scenario.getNetwork().addLink(link7);
        scenario.getNetwork().addLink(link8);


		PopulationFactory populationFactory = scenario.getPopulation().getFactory();

		for (int i = 0; i < 36000; i++) {
            Person person_1 = populationFactory.createPerson(Id.createPersonId("test_agent_1_" + i));
            Plan plan_1 = populationFactory.createPlan();
            Activity Activityhome1_1 = populationFactory.createActivityFromCoord("h", new Coord(1000, 1000));
            Activityhome1_1.setEndTime((6 + Math.random()) * 3600);
            Activity Activitywork_1 = populationFactory.createActivityFromCoord("w", new Coord(1000, -5500));
            Activitywork_1.setEndTime(Activityhome1_1.getEndTime() + 3600 + 3600 * 8);
            Activity Activityhome1_2 = populationFactory.createActivityFromCoord("h", new Coord(1000, 1000));
            Leg leg = populationFactory.createLeg("car");
            plan_1.addActivity(Activityhome1_1);
            plan_1.addLeg(leg);
            plan_1.addActivity(Activitywork_1);
            plan_1.addLeg(leg);
            plan_1.addActivity(Activityhome1_2);
            person_1.addPlan(plan_1);
            scenario.getPopulation().addPerson(person_1);

        }





		Controler controler = new Controler( scenario ) ;

		controler.run();

	}

}
