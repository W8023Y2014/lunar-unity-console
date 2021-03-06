//
//  ApplicationUITest.java
//
//  Lunar Unity Mobile Console
//  https://github.com/SpaceMadness/lunar-unity-console
//
//  Copyright 2016 Alex Lementuev, SpaceMadness.
//
//  Licensed under the Apache License, Version 2.0 (the "License");
//  you may not use this file except in compliance with the License.
//  You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
//  Unless required by applicable law or agreed to in writing, software
//  distributed under the License is distributed on an "AS IS" BASIS,
//  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//  See the License for the specific language governing permissions and
//  limitations under the License.
//

package spacemadness.com.lunarconsoleapp;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import org.junit.Test;
import org.junit.runner.RunWith;

import spacemadness.com.lunarconsole.console.ConsoleLogType;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ApplicationUITest extends ApplicationBaseUITest
{
    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Tests

    @Test
    public void testFilter()
    {
        logMessage("Debug-1", ConsoleLogType.LOG);
        logMessage("Debug-2", ConsoleLogType.LOG);
        logMessage("Warning-1", ConsoleLogType.WARNING);
        logMessage("Warning-2", ConsoleLogType.WARNING);
        logMessage("Error-1", ConsoleLogType.ERROR);
        logMessage("Error-2", ConsoleLogType.ERROR);

        pressButton(R.id.test_button_show_console);

        assertTable("Debug-1", "Debug-2", "Warning-1", "Warning-2", "Error-1", "Error-2");

        pressButton(R.id.lunar_console_log_button);
        assertTable("Warning-1", "Warning-2", "Error-1", "Error-2");

        pressButton(R.id.lunar_console_warning_button);
        assertTable("Error-1", "Error-2");

        pressButton(R.id.lunar_console_error_button);
        assertTable();

        pressButton(R.id.lunar_console_log_button);
        assertTable("Debug-1", "Debug-2");

        pressButton(R.id.lunar_console_warning_button);
        assertTable("Debug-1", "Debug-2", "Warning-1", "Warning-2");

        pressButton(R.id.lunar_console_error_button);
        assertTable("Debug-1", "Debug-2", "Warning-1", "Warning-2", "Error-1", "Error-2");

        clearText(R.id.lunar_console_text_edit_filter);

        appendText(R.id.lunar_console_text_edit_filter, "1");
        assertTable("Debug-1", "Warning-1", "Error-1");

        appendText(R.id.lunar_console_text_edit_filter, "1");
        assertTable();

        deleteLastChar(R.id.lunar_console_text_edit_filter);
        assertTable("Debug-1", "Warning-1", "Error-1");

        deleteLastChar(R.id.lunar_console_text_edit_filter);
        assertTable("Debug-1", "Debug-2", "Warning-1", "Warning-2", "Error-1", "Error-2");

        appendText(R.id.lunar_console_text_edit_filter, "2");
        assertTable("Debug-2", "Warning-2", "Error-2");

        appendText(R.id.lunar_console_text_edit_filter, "2");
        assertTable();

        clearText(R.id.lunar_console_text_edit_filter);
        assertTable("Debug-1", "Debug-2", "Warning-1", "Warning-2", "Error-1", "Error-2");
    }

    @Test
    public void testCollapse()
    {
        // add elements to console
        logMessage("Debug", ConsoleLogType.LOG);
        logMessage("Warning", ConsoleLogType.WARNING);
        logMessage("Error", ConsoleLogType.ERROR);
        logMessage("Debug", ConsoleLogType.LOG);
        logMessage("Warning", ConsoleLogType.WARNING);
        logMessage("Error", ConsoleLogType.ERROR);

        // present controller
        pressButton(R.id.test_button_show_console);

        // collapse elements
        pressButton(R.id.lunar_console_button_more);
        pressButton(getString(R.string.lunar_console_more_menu_collapse));

        assertTable("Debug@2", "Warning@2", "Error@2");

        // close controller
        pressButton(R.id.lunar_console_button_close);

        // re-open controller
        pressButton(R.id.test_button_show_console);

        assertTable("Debug@2", "Warning@2", "Error@2");

        // expand elements
        pressButton(R.id.lunar_console_button_more);
        pressButton(getString(R.string.lunar_console_more_menu_collapse));

        assertTable("Debug", "Warning", "Error", "Debug", "Warning", "Error");
    }

    @Test
    public void testOverflow()
    {
        // set trim
        typeText(R.id.test_edit_text_trim, "3");
        pressButton(R.id.test_button_set_trim);

        // set capacity
        typeText(R.id.test_edit_text_capacity, "5");
        pressButton(R.id.test_button_set_capacity);

        // add elements to console
        logMessage("Debug-1", ConsoleLogType.LOG);
        logMessage("Warning-1", ConsoleLogType.WARNING);
        logMessage("Error-1", ConsoleLogType.ERROR);
        logMessage("Debug-2", ConsoleLogType.LOG);
        logMessage("Warning-2", ConsoleLogType.WARNING);

        // show controller
        pressButton(R.id.test_button_show_console);

        // check table
        assertTable("Debug-1", "Warning-1", "Error-1", "Debug-2", "Warning-2");

        // overflow message should be invisible
        checkInvisible(R.id.lunar_console_text_overflow);

        // close controller
        pressButton(R.id.lunar_console_button_close);

        // make console overflow
        logMessage("Error-2", ConsoleLogType.ERROR);

        // show controller
        pressButton(R.id.test_button_show_console);

        // check table
        assertTable("Debug-2", "Warning-2", "Error-2");

        // overflow message should be visible
        checkVisible(R.id.lunar_console_text_overflow);

        // check overflow message
        checkText(R.id.lunar_console_text_overflow, String.format(getString(R.string.lunar_console_overflow_warning_text), 3));
    }
}
