package org.intermine.web;

/*
 * Copyright (C) 2002-2005 FlyMine
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  See the LICENSE file for more
 * information or http://www.gnu.org/copyleft/lesser.html.
 *
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;

import org.apache.struts.tiles.ComponentContext;

import servletunit.struts.MockStrutsTestCase;

public class TreeControllerTest extends MockStrutsTestCase
{
    public TreeControllerTest(String arg1) {
        super(arg1);
    }

    public void tearDown() throws Exception {
         getActionServlet().destroy();
    }

    public void testExecute() throws Exception {
        ComponentContext componentContext = new ComponentContext();
        ComponentContext.setContext(componentContext, getRequest());
        setRequestPathInfo("/initTree");

        String model = "org.intermine.model.testmodel.";
        Set openClasses = new HashSet();
        openClasses.add(model + "Employable");
        openClasses.add(model + "Thing");
        getSession().setAttribute("openClasses", openClasses);
        getRequest().setAttribute("rootClass", model + "Thing");
        //necessary to work-round struts test case not invoking our SessionListener
        getSession().setAttribute(Constants.PROFILE,
                                  new Profile(null, null, new HashMap(), new HashMap(), new HashMap()));

        actionPerform();
        verifyNoActionErrors();

        List expected = new ArrayList();
        expected.add(new TreeNode(model + "Thing", "", 0, false, false, true));
        expected.add(new TreeNode(model + "Address", "", 1, false, true, false));
        expected.add(new TreeNode(model + "Employable", "", 1, false, false, true));
        expected.add(new TreeNode(model + "Contractor", "", 2, false, true, false));
        expected.add(new TreeNode(model + "Employee", "", 2, false, false, false));

        assertEquals(openClasses, getSession().getAttribute("openClasses"));
        assertEquals(expected, componentContext.getAttribute("nodes"));
    }
}
