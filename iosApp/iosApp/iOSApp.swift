import SwiftUI
import shared

@main
struct iOSApp: App {
    init() {
        InitAppKt.doInitApp()
    }
    
	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}
