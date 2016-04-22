//
//  LUConsolePlugin.h
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

#import <Foundation/Foundation.h>

#import "LUObject.h"
#import "LUConsoleLogEntry.h"

@class LUActionRegistry;
@class LUConsole;
@class LUWindow;

typedef enum : NSUInteger {
    LUConsoleGestureNone,
    LUConsoleGestureSwipe
} LUConsoleGesture;

@interface LUConsolePlugin : LUObject

@property (nonatomic, readonly) LUWindow         * consoleWindow;
@property (nonatomic, readonly) LUWindow         * warningWindow;
@property (nonatomic, readonly) LUConsole        * console;
@property (nonatomic, readonly) LUActionRegistry * actionRegistry;

@property (nonatomic, assign) NSInteger capacity;
@property (nonatomic, assign) NSInteger trim;

- (instancetype)initWithTargetName:(NSString *)targetName
                        methodName:(NSString *)methodName
                           version:(NSString *)version
                          capacity:(NSUInteger)capacity
                         trimCount:(NSUInteger)trimCount
                       gestureName:(NSString *)gestureName;

- (void)show;
- (void)hide;

- (void)logMessage:(NSString *)message stackTrace:(NSString *)stackTrace type:(LUConsoleLogType)type;
- (void)clear;

- (void)registerActionWithId:(int)actionId name:(NSString *)name;
- (void)unregisterActionWithId:(int)actionId;
- (void)registerVariableWithId:(int)entryId name:(NSString *)name type:(NSString *)type value:(NSString *)value;

- (void)enableGestureRecognition;
- (void)disableGestureRecognition;

@end
